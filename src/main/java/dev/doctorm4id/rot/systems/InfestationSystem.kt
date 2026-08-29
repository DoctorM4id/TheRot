package dev.doctorm4id.rot.systems

import dev.doctorm4id.rot.content.ModContent
import dev.doctorm4id.stoatlib.util.PoolBlocks
import dev.doctorm4id.stoatlib.util.StoatBlockUtil

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.SculkChargeParticleOptions
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property

object InfestationSystem {

	private val randomCyst = PoolBlocks.apply {
		addEntry(ModContent.ROTTED_BLOCK, 100)
		addEntry(ModContent.BLOOMING_CYST_BLOCK, 10)
	}

	fun infestPosition(level: ServerLevel, pos: BlockPos) {
		level.playSound(
			null, pos,
			SoundEvents.SCULK_BLOCK_SPREAD,
			SoundSource.BLOCKS,
			2.0f, 0.4f + level.random.nextFloat() * 0.8f
		)

		level.sendParticles(
			SculkChargeParticleOptions(5.0f),
			pos.x + 0.5, pos.y + 0.5, pos.z + 0.5,
			4, 0.1, 0.1, 0.1, 0.2
		)

		infestBlock(level, pos)
	}

	private fun infestBlock(level: ServerLevel, pos: BlockPos) {
		val cyst = randomCyst.getRandomEntry() ?: return
		val blockState = level.getBlockState(pos)

		val newState = when (val block = level.getBlockState(pos).block) {
			is StairBlock -> ModContent.ROTTED_STAIR.defaultBlockState()
				.setValue(StairBlock.FACING, blockState.getValue(StairBlock.FACING))
				.setValue(StairBlock.HALF, blockState.getValue(StairBlock.HALF))
				.setValue(StairBlock.SHAPE, blockState.getValue(StairBlock.SHAPE))
				.setValue(BlockStateProperties.WATERLOGGED, blockState.getValue(BlockStateProperties.WATERLOGGED))

			is SlabBlock -> ModContent.ROTTED_SLAB.defaultBlockState()
				.setValue(SlabBlock.TYPE, blockState.getValue(SlabBlock.TYPE))
				.setValue(BlockStateProperties.WATERLOGGED, blockState.getValue(BlockStateProperties.WATERLOGGED))

			is LeavesBlock -> ModContent.ROTTED_LEAVES.defaultBlockState()
				.setValue(BlockStateProperties.WATERLOGGED, blockState.getValue(BlockStateProperties.WATERLOGGED))
				.setValue(LeavesBlock.PERSISTENT, true)

			else -> {
				if (StoatBlockUtil.isSolid(pos, level)) cyst.defaultBlockState() else return
			}
		}

		level.setBlock(pos, newState, 3)
	}
}
