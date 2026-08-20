package dev.doctorm4id.rot.platform.neoforge

//? neoforge {

import dev.doctorm4id.rot.TheRot
import dev.doctorm4id.rot.core.ModRegistry
import dev.doctorm4id.rot.event.ExampleEventHandler
import dev.doctorm4id.rot.systems.CursorManager
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegisterEvent


@EventBusSubscriber
object NeoforgeEventSubscriber {

	@JvmStatic
	@SubscribeEvent
	fun onPlayerDamage(event: LivingDamageEvent.Post) {
		if (event.entity is ServerPlayer && event.newDamage > 0) {

			val player = event.entity as ServerPlayer
			ExampleEventHandler().onPlayerHurt(player)
		}
	}

	@JvmStatic
	@SubscribeEvent
	fun onRegister(event: RegisterEvent) {
		ModRegistry.registerAll { id, supplier ->
			when (event.registryKey) {
				BuiltInRegistries.BLOCK.key() -> {
					val obj = supplier()
					if (obj is Block) {
						event.register(BuiltInRegistries.BLOCK.key(), id) { obj }
					}
				}
				BuiltInRegistries.ITEM.key() -> {
					val obj = supplier()
					if (obj is Item) {
						event.register(BuiltInRegistries.ITEM.key(), id) { obj }
					}
				}
			}
		}
	}

	@JvmStatic
	@SubscribeEvent
	fun serverTick(event: ServerTickEvent.Post) {
		CursorManager.tick()
	}
}

//? }
