package dev.doctorm4id.rot.core

import dev.doctorm4id.rot.common.block.RottedFloraBlock
import dev.doctorm4id.rot.util.RegistryUtil
import dev.doctorm4id.rot.util.RegistryUtil.registerBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.FenceBlock
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

object ModBlocks{

	/*val BLOOMING_CYST = registerBlock("blooming_cyst", BloomingRotCystBlock(BlockBehaviour.Properties.of()
		.sound(SoundType.HONEY_BLOCK)
		.mapColor(MapColor.COLOR_BLACK)
		.randomTicks())
	)*/
	val ROTTED_BLOCK = registerBlock("rotted_block", Block(BlockBehaviour.Properties.of()
		.sound(SoundType.HONEY_BLOCK)
		.mapColor(MapColor.COLOR_BLACK))
	)
	val ROTTED_LEAVES = registerBlock("rotted_leaves", LeavesBlock(BlockBehaviour.Properties.of()
		.sound(SoundType.HONEY_BLOCK)
		.mapColor(MapColor.COLOR_BLACK)
		.noOcclusion()
		.instabreak())
	)
	val ROTTED_STAIRS = registerBlock("rotted_stairs", StairBlock(
		ROTTED_BLOCK.defaultBlockState(),
		RegistryUtil.copyBlockProperties(ROTTED_BLOCK))
	)
	val ROTTED_SLAB = registerBlock("rotted_slab", SlabBlock(
		RegistryUtil.copyBlockProperties(ROTTED_BLOCK))
	)
	val ROTTED_FENCE = registerBlock("rotted_fence", FenceBlock(
		RegistryUtil.copyBlockProperties(ROTTED_BLOCK))
	)
	val ROTTED_WALL = registerBlock("rotted_wall", WallBlock(
		RegistryUtil.copyBlockProperties(ROTTED_BLOCK))
	)
	val ROTTED_GRASS = registerBlock("rotted_grass", RottedFloraBlock(BlockBehaviour.Properties.of()
		.sound(SoundType.HONEY_BLOCK)
		.mapColor(MapColor.COLOR_BLACK)
		.noCollission()
		.instabreak())
	)
	val ROTTED_GRASS_SHORT = registerBlock("rotted_grass_short", RottedFloraBlock(
		RegistryUtil.copyBlockProperties(ROTTED_GRASS)))

	fun init() {}
}
