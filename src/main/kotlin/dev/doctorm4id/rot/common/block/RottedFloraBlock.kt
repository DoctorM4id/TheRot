package dev.doctorm4id.rot.common.block

import com.mojang.serialization.MapCodec
import dev.doctorm4id.rot.core.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState

class RottedFloraBlock(properties : Properties) : BushBlock(properties) {
	override fun mayPlaceOn(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
		return blockState.`is`(ModBlocks.ROTTED_BLOCK)
	}

	//? if >= 1.20.5 {
	override fun codec(): MapCodec<out BushBlock?>? {
		return simpleCodec { properties -> RottedFloraBlock(properties) }
	}
	//? }
}
