package dev.doctorm4id.rot.platform.neoforge

//? neoforge {

import dev.doctorm4id.rot.event.ExampleEventHandler
import net.minecraft.server.level.ServerPlayer
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent

@EventBusSubscriber
class NeoforgeEventSubscriber {

	@SubscribeEvent
	fun onPlayerDamage(event: LivingDamageEvent.Post) {
		if (event.entity is ServerPlayer && event.newDamage > 0) {

			val player = event.entity as ServerPlayer
			ExampleEventHandler().onPlayerHurt(player)
		}
	}
}

//? }
