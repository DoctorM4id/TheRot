package dev.doctorm4id.rot.content

import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.content.block.BloomingCystBlock
import dev.doctorm4id.rot.content.block.RottedFloraBlock
import dev.doctorm4id.rot.content.item.CursorWand
import dev.doctorm4id.rot.content.item.InfestWand
import dev.doctorm4id.stoatlib.registration.StoatAutoRegistrar
import dev.doctorm4id.stoatlib.util.StoatCommonUtil
import io.ejekta.kambrik.ext.ResourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
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

object ModContent : StoatAutoRegistrar {

	val CURSOR_WAND_ITEM by "cursor_wand" forItem { CursorWand(Item.Properties().rarity(Rarity.EPIC) ) }

	val INFEST_WAND_ITEM by "infest_wand" forItem { InfestWand(Item.Properties().rarity(Rarity.EPIC) ) }



	val BLOOMING_CYST_BLOCK by "blooming_cyst" forBlock { BloomingCystBlock(BlockBehaviour.Properties.of()
		.sound(SoundType.HONEY_BLOCK)
		.mapColor(MapColor.COLOR_BLACK)
	) }
	val BLOOMING_CYST_ITEM by "blooming_cyst" forItem { BlockItem(ROTTED_BLOCK, Item.Properties().rarity(Rarity.RARE) ) }

	val ROTTED_BLOCK by "rotted_block" forBlock { Block(BlockBehaviour.Properties.of()
		.sound(SoundType.HONEY_BLOCK)
		.mapColor(MapColor.COLOR_BLACK)
	) }
	val ROTTED_BLOCK_ITEM by "rotted_block" forItem { BlockItem(ROTTED_BLOCK, Item.Properties().rarity(Rarity.UNCOMMON) ) }

	val ROTTED_SLAB by "rotted_slab" forBlock { SlabBlock(StoatCommonUtil.copyBlockProperties(ROTTED_BLOCK)) }
	val ROTTED_SLAB_ITEM by "rotted_slab" forItem { BlockItem(ROTTED_SLAB, Item.Properties()) }

	val ROTTED_STAIR by "rotted_stair" forBlock { StairBlock(ROTTED_BLOCK.defaultBlockState(), StoatCommonUtil.copyBlockProperties(ROTTED_BLOCK)) }
	val ROTTED_STAIR_ITEM by "rotted_stair" forItem { BlockItem(ROTTED_STAIR, Item.Properties()) }

	val ROTTED_WALL by "rotted_wall" forBlock { WallBlock(StoatCommonUtil.copyBlockProperties(ROTTED_BLOCK)) }
	val ROTTED_WALL_ITEM by "rotted_wall" forItem { BlockItem(ROTTED_WALL, Item.Properties()) }

	val ROTTED_FENCE by "rotted_fence" forBlock { FenceBlock(StoatCommonUtil.copyBlockProperties(ROTTED_BLOCK)) }
	val ROTTED_FENCE_ITEM by "rotted_fence" forItem { BlockItem(ROTTED_FENCE, Item.Properties()) }

	val ROTTED_LEAVES by "rotted_leaves" forBlock { LeavesBlock(StoatCommonUtil.copyBlockProperties(ROTTED_BLOCK).noOcclusion()) }
	val ROTTED_LEAVES_ITEM by "rotted_leaves" forItem { BlockItem(ROTTED_LEAVES, Item.Properties()) }

	val ROTTED_GRASS by "rotted_grass" forBlock { RottedFloraBlock(StoatCommonUtil.copyBlockProperties(ROTTED_BLOCK).noCollission().instabreak()) }
	val ROTTED_GRASS_ITEM by "rotted_grass" forItem { BlockItem(ROTTED_GRASS, Item.Properties()) }

	val NULL_CYAN by "null_cyan" forBlock { Block(BlockBehaviour.Properties.of() ) }

	val NULL_PINK by "null_pink" forBlock { Block(BlockBehaviour.Properties.of() ) }

	override fun getId(): String = TheRot.MOD_ID

	object BlockTags {
		val ROT_FAMILY: TagKey<Block> = create("rot_family")

		private fun create(name: String): TagKey<Block> {
			return TagKey.create(Registries.BLOCK, ResourceLocation(TheRot.MOD_ID, name))
		}

		private fun createMinecraft(name: String): TagKey<Block> {
			return TagKey.create(Registries.BLOCK, TheRot().id(name))
		}
	}
}
