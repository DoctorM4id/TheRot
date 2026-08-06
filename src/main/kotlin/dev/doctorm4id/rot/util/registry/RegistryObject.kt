package dev.doctorm4id.rot.util.registry

import net.minecraft.resources.ResourceLocation

class RegistryObject<T : Any>(val id: ResourceLocation, private val supplier: () -> T) {
	private lateinit var value: T

	internal fun register(registrar: (ResourceLocation, Any) -> Unit) {
		val obj = supplier()
		registrar(id, obj)
		value = obj
	}

	fun get(): T {
		if (!::value.isInitialized) error("Registry not initialized")
		return value
	}

	operator fun invoke(): T = get()
}
