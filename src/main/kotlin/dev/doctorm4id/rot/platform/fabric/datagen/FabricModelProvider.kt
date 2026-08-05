package dev.doctorm4id.rot.platform.fabric.datagen

//? fabric {

import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.core.ModBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators

class FabricModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {

	override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators?) {

		blockStateModelGenerator?.createTrivialCube(ModBlocks.ROTTED_LEAVES)
		blockStateModelGenerator?.family(ModBlocks.ROTTED_BLOCK)
			?.fence(ModBlocks.ROTTED_FENCE)
			?.slab(ModBlocks.ROTTED_SLAB)
			?.stairs(ModBlocks.ROTTED_STAIRS)
			?.wall(ModBlocks.ROTTED_WALL)
	}

	override fun generateItemModels(itemModelGenerator: ItemModelGenerators?) {

	}

	override fun getName(): String {

		return TheRot.MOD_FRIENDLY_NAME+" Model Provider"
	}
}

//?}
