package dev.doctorm4id.rot.systems

import dev.doctorm4id.rot.core.ModRegistry
import dev.doctorm4id.rot.util.BlockUtil
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

class VirtualSurfaceInfectorCursor(level: Level) : VirtualCursor(level) {

	override fun isTarget(pos: BlockPos): Boolean {
		val blockState = getWorld().getBlockState(pos)
		return !blockState.`is`(ModRegistry.NULL_CYAN())
	}

	override fun isObstructed(state: BlockState, pos: BlockPos): Boolean {
		val maxRangeSq = 16 * 16
		if (BlockUtil.getBlockDistanceSquared(origin, pos) > maxRangeSq) {
			return true
		} else if (BlockUtil.isAir(state)) {
			return true
		} else if (visitedPositions.contains(pos.asLong())) {
			return true
		} else if (!BlockUtil.isExposedToAir(pos, getWorld())) {
			return true
		} else if ((state.`is`(Blocks.WATER) || state.`is`(Blocks.BUBBLE_COLUMN))) {
			return true
		} else if (BlockUtil.isNotSolid(pos, getWorld())) {
			return true
		}/* else if (!BlockInfestationSystem.isValidInfectableBlock(pos, state, getWorld())) {
			return true
		}*/
		return false
	}
}
