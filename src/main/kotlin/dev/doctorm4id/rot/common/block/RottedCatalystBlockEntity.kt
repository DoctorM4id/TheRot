package dev.doctorm4id.rot.common.block

import dev.doctorm4id.rot.core.ModRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RottedCatalystBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(
	ModRegistry.ROTTED_CATALYST_ENTITY(), pos, state) {

}
