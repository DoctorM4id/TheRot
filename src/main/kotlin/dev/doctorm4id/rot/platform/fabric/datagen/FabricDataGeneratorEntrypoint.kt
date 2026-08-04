package dev.doctorm4id.rot.platform.fabric.datagen

//? fabric {

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

// I'm thinking that I can have and run datagen on fabric and not need datagen for neoforge.
class FabricDataGeneratorEntrypoint : DataGeneratorEntrypoint {

	override fun onInitializeDataGenerator(generator: FabricDataGenerator) {

		val pack = generator.createPack()

		pack.addProvider(::ModModelProvider)
	}
}

//?}
