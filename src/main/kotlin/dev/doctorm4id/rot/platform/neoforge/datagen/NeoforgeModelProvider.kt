package dev.doctorm4id.rot.platform.neoforge.datagen

//? neoforge {

/*import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.core.ModBlocks
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class NeoforgeModelProvider(output: PackOutput, existingFileHelper: ExistingFileHelper) : BlockModelProvider(output, TheRot.MOD_ID, existingFileHelper) {

	override fun registerModels() {

		leaves(ModBlocks.ROTTED_LEAVES.registeredName, modLoc("block/${ModBlocks.ROTTED_LEAVES.registeredName}"))

		val baseTexture = modLoc("block/${ModBlocks.ROTTED_BLOCK.registeredName}")

		cubeAll(ModBlocks.ROTTED_BLOCK.registeredName, baseTexture)

		fencePost("${ModBlocks.ROTTED_FENCE.registeredName}_post", baseTexture)
		fenceSide("${ModBlocks.ROTTED_FENCE.registeredName}_side", baseTexture)
		fenceInventory("${ModBlocks.ROTTED_FENCE.registeredName}_inventory", baseTexture)
		slab(ModBlocks.ROTTED_SLAB.registeredName, baseTexture, baseTexture, baseTexture)
		stairs(ModBlocks.ROTTED_STAIRS.registeredName, baseTexture, baseTexture, baseTexture)
		wallPost("${ModBlocks.ROTTED_WALL.registeredName}_post", baseTexture)
		wallSide("${ModBlocks.ROTTED_WALL.registeredName}_side", baseTexture)
		wallInventory("${ModBlocks.ROTTED_WALL.registeredName}_inventory", baseTexture)
	}

	private val Block.registeredName: String get() = BuiltInRegistries.BLOCK.getKey(this).path
}

*///? }
