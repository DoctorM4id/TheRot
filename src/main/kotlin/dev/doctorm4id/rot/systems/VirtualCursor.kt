package dev.doctorm4id.rot.systems

import dev.doctorm4id.rot.core.ModRegistry
import dev.doctorm4id.rot.util.BlockUtil
import dev.doctorm4id.rot.util.TickUtil
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import java.util.ArrayDeque
import java.util.UUID

open class VirtualCursor(private var level: Level) : ICursor {

	companion object {
		private val NEIGHBOR_OFFSETS: Array<BlockPos> = buildList {
			for (x in -1..1) {
				for (y in -1..1) {
					for (z in -1..1) {
						if (x != 0 || y != 0 || z != 0) {
							add(BlockPos(x, y, z))
						}
					}
				}
			}
		}.toTypedArray()

		private var ID = 0L
	}

	enum class State { SEARCHING, EXPLORING, FINISHED }

	private var pos: BlockPos = BlockPos.ZERO
	var origin: BlockPos = BlockPos.ZERO
	private var target: BlockPos? = BlockPos.ZERO

	private var state = State.SEARCHING
	private val id: Long = ++ID
	private var expired: Boolean = false

	private val creationTime: Long = getWorld().gameTime
	private val searchQueue: ArrayDeque<BlockPos> = ArrayDeque()
	private val positionsSearched: LongOpenHashSet = LongOpenHashSet()
	val visitedPositions: MutableSet<Long> = HashSet()

	protected open fun isTarget(pos: BlockPos): Boolean = !getWorld().getBlockState(pos).`is`(ModRegistry.NULL_CYAN())
	protected open fun changeBlock(pos: BlockPos) = getWorld().setBlock(pos, ModRegistry.NULL_GREEN().defaultBlockState(), 3)

	protected open fun isObstructed(state: BlockState, pos: BlockPos): Boolean {
		if (BlockUtil.getBlockDistanceSquared(origin, pos) > 20 * 20) return true
		if (BlockUtil.isAir(state)) return true
		if (visitedPositions.contains(pos.asLong())) return true

		return false
	}

	private fun hasExpired(): Boolean {
		return (getWorld().gameTime - creationTime) > TickUtil.convertMinutesToTicks(2)
	}

	override fun tick() {
		if (hasExpired()) {
			setState(State.FINISHED)
			return
		}

		if (origin == BlockPos.ZERO) origin = pos

		when (state) {
			State.SEARCHING -> {if (searchTick()) finishSearch(target)
				println("searching")}
			State.EXPLORING -> {exploreTick()
				println("exploring")}
			State.FINISHED -> {setExpired()
				println("expired")}
		}
	}

	private fun startSearch() {
		searchQueue.clear()
		positionsSearched.clear()
		target = null
		searchQueue.add(pos)
		setState(State.SEARCHING)
	}

	private fun searchTick(): Boolean {
		val iterations = 64

		repeat(iterations) {
			if (searchQueue.isEmpty()) return true

			val currentBlock = searchQueue.poll() ?: return@repeat

			if (currentBlock != pos && isTarget(currentBlock)) {
				target = currentBlock
				return true
			}

			val FACKYOU = getWorld().random.nextInt(NEIGHBOR_OFFSETS.size)

			for (i in NEIGHBOR_OFFSETS.indices) {
				val offset = NEIGHBOR_OFFSETS[(FACKYOU + i) % NEIGHBOR_OFFSETS.size]
				val neighbor = currentBlock.offset(offset)
				val longPos = neighbor.asLong()

				if (positionsSearched.add(longPos) && !isObstructed(getWorld().getBlockState(neighbor), neighbor)) {
					searchQueue.add(neighbor)
				}
			}
		}

		return false
	}

	private fun finishSearch(foundTarget: BlockPos?) {
		if (foundTarget == null) {
			setState(State.FINISHED)
		} else {
			visitedPositions.clear()
			setState(State.EXPLORING)
		}
	}

	private fun exploreTick() {
		val currentTarget = target

		if (currentTarget == null) {
			startSearch()
			return
		}

		var closet: BlockPos = BlockPos.ZERO
		var minDistanceSq = Long.MAX_VALUE

		println("exploring | stage ONE | $pos |")
		getWorld().setBlock(pos, ModRegistry.NULL_CYAN().defaultBlockState(), 3)

		for (offset in NEIGHBOR_OFFSETS) {
			val neighbor = pos.offset(offset)
			if (!isObstructed(getWorld().getBlockState(neighbor), neighbor)) {
				val distSq = BlockUtil.getBlockDistanceSquared(neighbor, currentTarget)

				if (distSq < minDistanceSq) {
					minDistanceSq = distSq.toLong()
					closet = neighbor
				}
			}
		}

		moveTo(closet.x, closet.y, closet.z)

		println("exploring | stage TWO | $pos")
		getWorld().setBlock(pos, ModRegistry.NULL_BLUE().defaultBlockState(), 3)

		val targetCheck = isTarget(pos)
		val obstructedCheck = !isObstructed(getWorld().getBlockState(pos), pos)
		println("Debug -> isTarget: $targetCheck, isObstructed: $obstructedCheck")

		if (targetCheck && obstructedCheck) {
			changeBlock(pos)
			startSearch()
		} else {
			println("exploring | stage TWO | $pos | :c")
			visitedPositions.add(closet.asLong())
		}
	}

	override fun getID(): UUID = UUID(0L, id)

	override fun getWorld(): Level = level
	override fun getPos(): BlockPos? = pos
	override fun moveTo(x: Int, y: Int, z: Int) { pos = BlockPos(x, y, z) }
	override fun setState(newState: State) { state = newState }

	override fun isExpired(): Boolean = expired
	override fun setExpired() { expired = true }
}
