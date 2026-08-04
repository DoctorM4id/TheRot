package dev.doctorm4id.rot.platform.fabric

//? fabric {

import dev.doctorm4id.rot.TheRot
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint
import net.fabricmc.api.ClientModInitializer

@Entrypoint("client")
class FabricClientEntrypoint : ClientModInitializer {

	override fun onInitializeClient() {
		TheRot().onInitializeClient()
	}
}

//? }
