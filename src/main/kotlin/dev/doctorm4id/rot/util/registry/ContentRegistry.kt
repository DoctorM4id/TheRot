package dev.doctorm4id.rot.util.registry

import dev.doctorm4id.rot.util.CommonUtil
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

// Blehg, started to write javadocs for everythin... because idk I am bored and looks cool.

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
 * 		ModRegistry.registerAll { id, obj ->
 * 			when (obj) {
 * 				is Block -> Registry.register(BuiltInRegistries.BLOCK, id, obj)
 * 				is Item -> Registry.register(BuiltInRegistries.ITEM, id, obj)
 * 			}
 * 		}
 * ```
 *
 * **Neoforge:** (I hate forge... I SWEAR I THINK IT WORKED, IDK WTH I DID.)
 * ```
 * 		ModRegistry.registerAll { id, obj ->
 * 			when (obj) {
 * 				is Block -> event.register(BuiltInRegistries.BLOCK.key(), id) { obj }
 * 				is Item -> event.register(BuiltInRegistries.ITEM.key(), id) { obj }
 * 			}
 * 		}
 * ```
 *
 */
abstract class ContentRegistry(val modId: String) {

	private val entries = mutableListOf<RegistryObject<*>>()

	/**
	 * Creates a [RegistryObject] then adds it to the [entries].
	 *
	 * @param name The registry name
	 * @param supplier A lambda that creates instance.
	 * @return The new [RegistryObject]
	 */
	protected fun <T : Any> createRegistryObject(name: String, supplier: () -> T): RegistryObject<T> {

		val id = CommonUtil.id(modId, name)
		return RegistryObject(id, supplier).also { entries.add(it) }
	}

	/**
	 * Registers all entries.
	 *
	 * @param registrar Function that takes a [ResourceLocation] plus instance and registers it.
	 *
	 * For Fabric, pass the instance directly.
	 * For Neoforge, wrap it in a supplier.
	 */
	fun registerAll(registrar: (ResourceLocation, Any) -> Unit) {

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
	protected fun block(supplier: () -> Block): ReadOnlyProperty<ContentRegistry, RegistryObject<Block>> =
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
	protected fun item(supplier: () -> Item): ReadOnlyProperty<ContentRegistry, RegistryObject<Item>> =
		registryDelegate(supplier)




	/**
	 * Helper
	 */
	private inline fun <reified T : Any> registryDelegate(noinline supplier: () -> T): ReadOnlyProperty<ContentRegistry, RegistryObject<T>> = object : ReadOnlyProperty<ContentRegistry, RegistryObject<T>> {

		private var delegate: RegistryObject<T>? = null

		override fun getValue(thisRef: ContentRegistry, property: KProperty<*>): RegistryObject<T> {
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
 * Pair of [RegistryObject]s for a block and item.
 * To get the block is something like this:
 *
 * ```MY_BLOCK.get()``` or: ```MY_BLOCK()```
 *
 * @param block The block registry object
 * @param item The item registry object.
 */
data class BlockWithItem(val block: RegistryObject<Block>, val item: RegistryObject<BlockItem>) {

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
