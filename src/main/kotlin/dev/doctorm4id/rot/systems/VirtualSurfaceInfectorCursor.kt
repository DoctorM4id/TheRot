package dev.doctorm4id.rot.systems

import dev.doctorm4id.rot.core.ModRegistry
import dev.doctorm4id.rot.util.BlockUtil
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

class VirtualSurfaceInfectorCursor(level: ServerLevel) : VirtualCursor(level) {

	override fun isTarget(level: Level, pos: BlockPos): Boolean {
		val blockState = getWorld().getBlockState(pos)
		return !blockState.`is`(ModRegistry.BlockTags.ROT_FAMILY) && shouldInfest(level, pos)
	}

	override fun changeBlock(pos: BlockPos) {
		InfestationSystem.infestPosition(getWorld() as ServerLevel, pos)
	}

	override fun isObstructed(state: BlockState, pos: BlockPos): Boolean {
		if (BlockUtil.isAir(state)) {
			return true
		} else if (visitedPositions.contains(pos.asLong())) {
			return true
		} else if ((state.`is`(Blocks.WATER) || state.`is`(Blocks.BUBBLE_COLUMN))) {
			return true
		} else if (BlockUtil.isNotSolid(pos, getWorld())) {
			return true
		}

		return false
	}

	fun shouldInfest(level: Level, pos: BlockPos): Boolean {
		val neighbors = BlockUtil.getNeighborsCube(pos, false).filterNotNull()

		val rotNeighbors = neighbors.count { level.getBlockState(it).`is`(ModRegistry.BlockTags.ROT_FAMILY) }
		val exposed = if (BlockUtil.isExposedToAir(pos, level)) 1.0 else 0.05
		//val darkness = 15 - level.getMaxLocalRawBrightness(pos)
		val wetBonus = if (level.getFluidState(pos).isSource) 0.2 else 0.0
		val distance = if (BlockUtil.getBlockDistanceSquared(origin, pos) < 10 * 10) 1.0 else 0.2
		val maxDistance = if (BlockUtil.getBlockDistanceSquared(origin, pos) < 15 * 15) 1.0 else 0.1

		val base = 0.02
		val chance = (((base + rotNeighbors * 0.1 /*+ darkness * 0.01*/ + wetBonus) * exposed) * distance) * maxDistance
		return level.random.nextDouble() < chance.coerceIn(0.0, 0.95)
	}
}
