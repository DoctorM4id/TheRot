package dev.doctorm4id.rot.stoatutil.registry

import dev.doctorm4id.rot.stoatutil.StoatCommonUtil
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

/**
 * Base class for a mod's registry container.
 * Designed to work with neoforge + fabric.
 *
 * # Usage
 *
 * ### First:
 * Extend the class with your own `object` :
 * ```
 * object ModRegistry : ContentRegistry("your_mod_id") {
 *
 * 	val MY_BLOCK by block {
 * 		Block(BlockBehaviour.Properties.of())
 * 	}
 *
 * 	val MY_BLOCK_WITH_ITEM by blockWithItem {
 * 		Block(BlockBehaviour.Properties.of())
 * 	}
 *
 * 	val MY_ITEM by item {
 * 		Item(Item.Properties())
 * 	}
 * }
 * ```
 *
 * ### Second:
 * Call the `registerAll` from the respected platform.
 *
 * Might make this easier in the future.
 *
 * **Fabric:**
 * ```
 * 	ModRegistry.registerAll { id, supplier ->
 * 		val obj = supplier()
 * 		when (obj) {
 * 			is Block -> Registry.register(BuiltInRegistries.BLOCK, id, obj)
 * 			is Item -> Registry.register(BuiltInRegistries.ITEM, id, obj)
 * 			else -> {}
 * 		}
 * 	}
 * ```
 *
 * **Neoforge:**
 * ```
 * 	@JvmStatic
 * 	@SubscribeEvent
 * 	fun onRegister(event: RegisterEvent) {
 * 		ModRegistry.registerAll { id, supplier ->
 * 			when (event.registryKey) {
 * 				BuiltInRegistries.BLOCK.key() -> {
 * 					val obj = supplier()
 * 					if (obj is Block) {
 * 						event.register(BuiltInRegistries.BLOCK.key(), id) { obj }
 * 					}
 * 				}
 * 				BuiltInRegistries.ITEM.key() -> {
 * 					val obj = supplier()
 * 					if (obj is Item) {
 * 						event.register(BuiltInRegistries.ITEM.key(), id) { obj }
 * 					}
 * 				}
 * 			}
 * 		}
 * 	}
 * ```
 *
 */
abstract class ContentRegistry(val modId: String) {

	private val entries = mutableListOf<StoatRegistryObject<*>>()

	/**
	 * Creates a [StoatRegistryObject] then adds it to the [entries].
	 *
	 * @param name The registry name
	 * @param supplier A lambda that creates instance.
	 * @return The new [StoatRegistryObject]
	 */
	protected fun <T : Any> createRegistryObject(name: String, supplier: () -> T): StoatRegistryObject<T> {

		val id = StoatCommonUtil.id(modId, name)
		return StoatRegistryObject(id, supplier).also { entries.add(it) }
	}

	/**
	 * Registers all entries.
	 *
	 * @param registrar Function that takes a [ResourceLocation] plus instance and registers it.
	 *
	 * For Fabric, pass the instance directly.
	 * For Neoforge, wrap it in a supplier.
	 */
	fun registerAll(registrar: (ResourceLocation, () -> Any) -> Unit) {

		if (entries.isEmpty()) {
			this::class.memberProperties.forEach { property ->
				property.getter.call(this)
			}
		}

		entries.forEach { it.register(registrar) }
	}




	/**
	 * Registers a [Block], **without** an item
	 * ```
	 * 	val MY_BLOCK by block {
	 * 		Block(BlockBehaviour.Properties.of())
	 * 	}
	 * ```
	 */
	protected fun block(supplier: () -> Block): ReadOnlyProperty<ContentRegistry, StoatRegistryObject<Block>> =
		registryDelegate(supplier)

	/**
	 * Registers a [Block], and a [BlockItem]
	 *
	 * ```
	 * 	val MY_BLOCK by blockWithItem {
	 * 		Block(BlockBehaviour.Properties.of())
	 * 	}
	 * ```
	 */
	protected fun blockWithItem(supplier: () -> Block): ReadOnlyProperty<ContentRegistry, BlockWithItem> = object : ReadOnlyProperty<ContentRegistry, BlockWithItem> {
		private var delegate: BlockWithItem? = null

		override fun getValue(thisRef: ContentRegistry, property: KProperty<*>): BlockWithItem {
			val existing = delegate
			if (existing != null) return existing

			val name = property.name.lowercase()
			val blockObj = thisRef.createRegistryObject(name, supplier)

			val itemSupplier = { BlockItem(blockObj.get(), Item.Properties()) }
			val itemObj = thisRef.createRegistryObject(name, itemSupplier)

			val new = BlockWithItem(blockObj, itemObj)
			delegate = new
			return new
		}
	}

	/**
	 * Registers a [Item].
	 * ```
	 * 	val MY_ITEM by item {
	 * 		Item(Item.Properties()
	 * 	}
	 * ```
	 */
	protected fun item(supplier: () -> Item): ReadOnlyProperty<ContentRegistry, StoatRegistryObject<Item>> =
		registryDelegate(supplier)




	/**
	 * Helper
	 */
	private inline fun <reified T : Any> registryDelegate(noinline supplier: () -> T): ReadOnlyProperty<ContentRegistry, StoatRegistryObject<T>> = object : ReadOnlyProperty<ContentRegistry, StoatRegistryObject<T>> {

		private var delegate: StoatRegistryObject<T>? = null

		override fun getValue(thisRef: ContentRegistry, property: KProperty<*>): StoatRegistryObject<T> {
			val existing = delegate
			if (existing != null) return existing

			val name = property.name.lowercase()
			val new = thisRef.createRegistryObject(name, supplier)
			delegate = new
			return new
		}
	}
}

/**
 * Pair of [StoatRegistryObject]s for a block and item.
 * To get the block is something like this:
 *
 * ```MY_BLOCK.get()``` or: ```MY_BLOCK()```
 *
 * @param block The block registry object
 * @param item The item registry object.
 */
data class BlockWithItem(val block: StoatRegistryObject<Block>, val item: StoatRegistryObject<BlockItem>) {

	fun get(): Block {
		return block.get()
	}

	/**
	 * Operator to get the block directly.
	 *
	 * **Example:**
	 * ```
	 * val block = MY_BLOCK()
	 * ```
	 */
	operator fun invoke(): Block = get()
}
