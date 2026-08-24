package dev.doctorm4id.rot.systems

import dev.doctorm4id.rot.content.ModContent
import dev.doctorm4id.stoatlib.util.PoolBlocks
import dev.doctorm4id.stoatlib.util.StoatBlockUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.SculkChargeParticleOptions
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property

object InfestationSystem {

	private val randomCyst = PoolBlocks.apply {
		addEntry(ModContent.ROTTED_BLOCK, 150)
		//addEntry(ModRegistry.BLOOMING_CYST(), 10)
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

		val newState = when (blockState) {
/*			is StairBlock -> ModRegistry.ROTTED_STAIR().defaultBlockState()
				.copyProperties(blockState, StairBlock.FACING, StairBlock.HALF, StairBlock.SHAPE, BlockStateProperties.WATERLOGGED)
			is SlabBlock -> ModRegistry.ROTTED_SLAB().defaultBlockState()
				.copyProperties(blockState, SlabBlock.TYPE, BlockStateProperties.WATERLOGGED)*/
			else -> {
				if (StoatBlockUtil.isSolid(pos, level)) cyst.defaultBlockState() else return
			}
		}

		level.setBlock(pos, newState, 3)
	}

	private fun BlockState.copyProperties(source: BlockState, vararg properties: Property<*>): BlockState {
		var current = this

		for (prop in properties) {
			if (source.hasProperty(prop) && current.hasProperty(prop)) {
				current = copyProp(source, current, prop)
			}
		}

		return current
	}

	private fun <T : Comparable<T>> copyProp(source: BlockState, target: BlockState, prop: Property<T>): BlockState {
		return target.setValue(prop, source.getValue(prop))
	}
}
