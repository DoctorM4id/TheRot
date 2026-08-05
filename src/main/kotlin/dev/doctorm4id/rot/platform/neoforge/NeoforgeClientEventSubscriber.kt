package dev.doctorm4id.rot.platform.neoforge

//? neoforge {

/*import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.platform.neoforge.datagen.NeoforgeModelProvider
//? <= 1.20.1 {
/*import dev.doctorm4id.rot.core.ModRenderLayers
*///? }
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid = TheRot.MOD_ID, value = [Dist.CLIENT])
object NeoforgeClientEventSubscriber {

	@JvmStatic
	@SubscribeEvent
	fun onClientSetup(event: FMLClientSetupEvent) {
		TheRot().onInitializeClient()

		//? <= 1.20.1 {
		/*event.enqueueWork {
			ModRenderLayers.CUTOUT_BLOCKS.forEach { block ->
				ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout())
			}
		}
		*///? }
	}
}
*///? }
