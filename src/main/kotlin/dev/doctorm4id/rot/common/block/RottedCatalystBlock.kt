package dev.doctorm4id.rot.common.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RottedCatalystBlock(properties: Properties) : BaseEntityBlock(properties)  {
	override fun codec(): MapCodec<out BaseEntityBlock?> {
		return simpleCodec { properties -> RottedCatalystBlock(properties) }
	}

	override fun newBlockEntity(
		p0: BlockPos,
		p1: BlockState
	): BlockEntity? {
		TODO("Not yet implemented")
	}
}
