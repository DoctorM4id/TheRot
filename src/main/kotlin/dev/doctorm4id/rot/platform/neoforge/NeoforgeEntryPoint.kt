package dev.doctorm4id.rot.platform.neoforge

//? neoforge {

import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.core.ModRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.fml.common.Mod

@Mod(TheRot.MOD_ID)
class NeoforgeEntryPoint {

	init {
		TheRot().onInitialize()
	}
}

//? }
