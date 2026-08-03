package dev.doctorm4id.rot.platform.fabric.datagen

import dev.doctorm4id.rot.TheRot
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators

class ModModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {

	override fun generateBlockStateModels(blockModelGenerator: BlockModelGenerators?) {

	}

	override fun generateItemModels(itemModelGenerator: ItemModelGenerators?) {

	}

	override fun getName(): String {
		return TheRot.MOD_FRIENDLY_NAME+" Model Provider"
	}
}
