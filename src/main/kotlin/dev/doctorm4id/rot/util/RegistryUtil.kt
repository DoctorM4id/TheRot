package dev.doctorm4id.rot.util

import dev.doctorm4id.rot.TheRot
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object RegistryUtil {

	/**
	 * Registers a BLOCK.
	 * @param name String value of resource location path, uses rot as namespace.
	 * @param block Block . . .
	 * @param registerItem Should register item, true by default.
	 * @return too lazy
	 */
	fun registerBlock(name: String, block: Block, registerItem: Boolean = true): Block {
		if (registerItem) registerBlockItem(name, block)

		return Registry.register(
			BuiltInRegistries.BLOCK,
			TheRot().id(name),
			block)
	}

	/**
	 * Registers a BLOCK ITEM.
	 * @param name String value of resource location path, uses rot as namespace.
	 * @param block Block . . .
	 * @return too lazy
	 */
	private fun registerBlockItem(name: String, block: Block): Item {
		val item = BlockItem(block, Item.Properties())

		return Registry.register(BuiltInRegistries.ITEM, TheRot().id(name), item)
	}

	/**
	 * Registers a ITEM.
	 * @param name String value of resource location path, uses rot as namespace.
	 * @param itemFactory Item factory.
	 * @param settings Item properties.
	 * @return too lazy
	 */
	fun registerItem(name: String, itemFactory: (Item.Properties) -> Item, settings: Item.Properties): Item {
		val item = itemFactory(settings)

		return Registry.register(
			BuiltInRegistries.ITEM,
			TheRot().id(name),
			item)
	}
}
