package dev.doctorm4id.rot.stoatutil.registry

import net.minecraft.resources.ResourceLocation

class StoatRegistryObject<T : Any>(val id: ResourceLocation, private val supplier: () -> T) {
	private var value: T? = null

	internal fun register(registrar: (ResourceLocation, () -> T) -> Unit) {
		registrar(id) {
			val existing = value
			if (existing != null) return@registrar existing
			val created = supplier()
			value = created
			created
		}
	}

	fun get(): T {
		val existing = value
		if (existing != null) return existing
		val created = supplier()
		value = created
		return created
	}

	operator fun invoke(): T = get()
}
