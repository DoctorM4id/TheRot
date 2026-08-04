@file:Suppress("unused")
package dev.doctorm4id.rot.core

import dev.doctorm4id.rot.common.item.CursorWand
import dev.doctorm4id.rot.util.RegistryUtil
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity

object ModItems {
	val CURSOR_WAND = RegistryUtil.registerItem("cursor_wand",::CursorWand, Item.Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant())

	fun init() {}
}
