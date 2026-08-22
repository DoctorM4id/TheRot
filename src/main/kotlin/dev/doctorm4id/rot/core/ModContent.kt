package dev.doctorm4id.rot.core

import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.common.block.RottedBlock
import dev.doctorm4id.rot.common.item.CursorWand
import io.ejekta.kambrik.ext.ResourceLocation
import io.ejekta.kambrik.registration.KambrikAutoRegistrar
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

object ModContent : KambrikAutoRegistrar {

	val CURSOR_WAND_ITEM by "cursor_wand" forItem { CursorWand(Item.Properties()) }

	val ROTTED_BLOCK by "rotted_block" forBlock { RottedBlock(BlockBehaviour.Properties.of()) }
	val ROTTED_BLOCK_ITEM by "rotted_block" forItem { BlockItem(ROTTED_BLOCK, Item.Properties()) }

	val NULL_CYAN by "null_cyan" forBlock { Block(BlockBehaviour.Properties.of()) }

	val NULL_PINK by "null_pink" forBlock { Block(BlockBehaviour.Properties.of()) }

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
