package dev.doctorm4id.rot.platform.fabric

//? fabric {

/*import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.core.ModRegistry
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint
import net.fabricmc.api.ModInitializer
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity

@Entrypoint("main")
class FabricEntrypoint : ModInitializer {

	override fun onInitialize() {
		TheRot().onInitialize()
		FabricEventSubscriber().registerEvents()

		ModRegistry.registerAll { id, supplier ->
			val obj = supplier()
			when (obj) {
				is Block -> Registry.register(BuiltInRegistries.BLOCK, id, obj)
				is Item -> Registry.register(BuiltInRegistries.ITEM, id, obj)
				else -> {}
			}
		}
	}
}

*///? }
