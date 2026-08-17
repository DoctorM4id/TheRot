package dev.doctorm4id.rot.core

import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.common.block.BloomingCystBlock
import dev.doctorm4id.rot.common.block.RottedFloraBlock
import dev.doctorm4id.rot.common.item.CursorWand
import dev.doctorm4id.rot.util.CommonUtil
import dev.doctorm4id.rot.util.registry.ContentRegistry
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.FenceBlock
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

object ModRegistry : ContentRegistry(TheRot.MOD_ID) {

	val BLOOMING_CYST by blockWithItem {
		BloomingCystBlock(BlockBehaviour.Properties.of()
			.sound(SoundType.HONEY_BLOCK)
			.mapColor(MapColor.COLOR_BLACK)
			.randomTicks()
		)}

	val ROTTED_BLOCK by blockWithItem {
		Block(BlockBehaviour.Properties.of()
				.sound(SoundType.HONEY_BLOCK)
				.mapColor(MapColor.COLOR_BLACK)
		)}

	val ROTTED_STAIR by blockWithItem {
		StairBlock(
			ROTTED_BLOCK().defaultBlockState(),
			CommonUtil.copyBlockProperties(ROTTED_BLOCK())
		)}

	val ROTTED_SLAB by blockWithItem {
		SlabBlock(
			CommonUtil.copyBlockProperties(ROTTED_BLOCK())
		)}

	val ROTTED_WALL by blockWithItem {
		WallBlock(
			CommonUtil.copyBlockProperties(ROTTED_BLOCK())
		)}

	val ROTTED_FENCE by blockWithItem {
		FenceBlock(
			CommonUtil.copyBlockProperties(ROTTED_BLOCK())
		)}

	val ROTTED_LEAVES by blockWithItem {
		LeavesBlock(BlockBehaviour.Properties.of()
			.sound(SoundType.HONEY_BLOCK)
			.mapColor(MapColor.COLOR_BLACK)
			.noOcclusion()
		)}

	val ROTTED_GRASS by blockWithItem {
		RottedFloraBlock(BlockBehaviour.Properties.of()
			.sound(SoundType.HONEY_BLOCK)
			.mapColor(MapColor.COLOR_BLACK)
			.noCollission()
			.instabreak()
		)}

	val ROTTED_GRASS_SHORT by blockWithItem {
		RottedFloraBlock(
			CommonUtil.copyBlockProperties(ROTTED_GRASS())
		)}

	val NULL_CYAN by blockWithItem {
		Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_CYAN)
			.noOcclusion()
		)}

	val NULL_GREEN by blockWithItem {
		Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_GREEN)
			.noOcclusion()
		)}

	val NULL_RED by blockWithItem {
		Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_RED)
			.noOcclusion()
		)}

	val NULL_WHITE by blockWithItem {
		Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.noOcclusion()
		)}

	val NULL_PINK by blockWithItem {
		Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_PINK)
			.noOcclusion()
		)}

	val NULL_BLUE by blockWithItem {
		Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_BLUE)
			.noOcclusion()
		)}

	val NULL_YELLOW by blockWithItem {
		Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_YELLOW)
			.noOcclusion()
		)}

	val CURSOR_WAND by item {
		CursorWand(Item.Properties()
			.rarity(Rarity.EPIC)
			.stacksTo(1)
			.fireResistant()
		)}

	object BlockTags {
		val ROT_FAMILY: TagKey<Block> = create("rot_family")

		private fun create(name: String): TagKey<Block> {
			return TagKey.create(Registries.BLOCK, TheRot().id(name))
		}

		private fun createMinecraft(name: String): TagKey<Block> {
			return TagKey.create(Registries.BLOCK, TheRot().id(name))
		}
	}
}
