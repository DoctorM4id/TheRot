package dev.doctorm4id.rot.systems

import dev.doctorm4id.rot.core.ModContent
import dev.doctorm4id.rot.core.ModRegistry
import dev.doctorm4id.stoatlib.util.StoatBlockUtil
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import kotlin.times

class VirtualSurfaceInfectorCursor(level: ServerLevel) : VirtualCursor(level) {

	override fun isTarget(level: Level, pos: BlockPos): Boolean {
		val blockState = getWorld().getBlockState(pos)
		return !blockState.`is`(ModContent.BlockTags.ROT_FAMILY)// && shouldInfest(level, pos)
	}

	override fun changeBlock(pos: BlockPos) {
		InfestationSystem.infestPosition(getWorld() as ServerLevel, pos)
		//getWorld().setBlock(pos, ModContent.ROTTED_BLOCK.defaultBlockState(), 3)
	}

	override fun isObstructed(state: BlockState, pos: BlockPos): Boolean {

		if (StoatBlockUtil.isAir(state)) {
			return true
		} else if (visitedPositions.contains(pos.asLong())) {
			return true
		} else if ((state.`is`(Blocks.WATER) || state.`is`(Blocks.BUBBLE_COLUMN))) {
			return true
		} else if (StoatBlockUtil.isNotSolid(pos, getWorld()) && false) {
			return true
		}

		return !shouldInfest(getWorld(), pos)
	}

	fun shouldInfest(level: Level, pos: BlockPos): Boolean {
		val neighbors = StoatBlockUtil.getNeighborsCube(pos, false).filterNotNull()

		val rotNeighbors = neighbors.count { level.getBlockState(it).`is`(ModContent.BlockTags.ROT_FAMILY) }
		val exposed = if (StoatBlockUtil.isExposedToAir(pos, level)) 1.0 else 0.05
		val wetBonus = if (level.getFluidState(pos).isSource) 0.2 else 0.0
		val distance = if (StoatBlockUtil.getBlockDistanceSquared(origin, pos) < 10 * 10) 1.0 else 0.2

		val base = 0.05
		val chance = (base + (rotNeighbors * 0.4).coerceIn(0.0, 1.0))

		val willInfest = level.random.nextDouble() < chance.coerceIn(0.0, 1.0)

		return willInfest
	}

/*	fun shouldInfest(pos: BlockPos): Boolean {
		val neighbors = StoatBlockUtil.getNeighborsCube(pos, false).filterNotNull()

		val rotNeighbors = neighbors.count { level.getBlockState(it).`is`(ModRegistry.BlockTags.ROT_FAMILY) }
		val exposed = if (StoatBlockUtil.isExposedToAir(pos, getWorld())) 1.0 else 0.05
		//val darkness = 15 - level.getMaxLocalRawBrightness(pos)
		val wetBonus = if (getWorld().getFluidState(pos).isSource) 0.2 else 0.0
		//val distance = if (StoatBlockUtil.getBlockDistanceSquared(origin, pos) < 10 * 10) 1.0 else 0.2
		//val maxDistance = if (StoatBlockUtil.getBlockDistanceSquared(origin, pos) < 15 * 15) 1.0 else 0.1

		val base = 0.02
		val chance = ((base + (rotNeighbors * 0.1) *//*+ darkness * 0.01*//* + wetBonus) * exposed)// * distance) * maxDistance
		return getWorld().random.nextDouble() < chance.coerceIn(0.0, 0.95)
	}*/
}
