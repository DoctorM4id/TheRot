@file:Suppress("unused")
package dev.doctorm4id.rot.util

import net.minecraft.world.level.block.Block
import kotlin.random.Random

object PoolBlocks {
	private var totalWeight: Int = 0
	private var entries: ArrayList<PoolEntry> = ArrayList()

	/**
	 * Adds an entry
	 * @param blockIn the block
	 * @param weightIn the weight of the entry
	 */
	fun addEntry(blockIn: Block?, weightIn: Int) {
		if (weightIn <= 0) return
		totalWeight += weightIn
		val poolEntry = PoolEntry(blockIn, weightIn)
		entries.add(poolEntry)
	}

	/**
	 * Adds an experimental entry
	 * @param blockIn the block
	 * @param weightIn the weight of the entry
	 */
	fun addExperimentalEntry(blockIn: Block?, weightIn: Int) {
		if (weightIn <= 0) return
		totalWeight += weightIn
		val poolEntry = PoolEntry(blockIn, weightIn)
		poolEntry.requireExperimentalMode()
		entries.add(poolEntry)
	}

	/**
	 * Returns a random entry.
	 * @return a random block.
	 */
	fun getRandomEntry(): Block? {
		if (totalWeight <= 0 || entries.isEmpty()) return null

		val randomValue = Random.nextInt(totalWeight)
		var cumulativeSum = 0
		for (entry in entries) {
			cumulativeSum += entry.weight
			if (randomValue < cumulativeSum) return entry.block
		}
		return entries.lastOrNull()?.block
	}
}

class PoolEntry(val block: Block?, var weight: Int) : Comparable<PoolEntry> {
	var requiresExperimentalMode: Boolean = false

	fun requireExperimentalMode() {
		requiresExperimentalMode = true
	}

	fun doesRequireExperimentalMode(): Boolean {
		return requiresExperimentalMode
	}

	override fun compareTo(other: PoolEntry): Int {
		return this.weight.compareTo(other.weight)
	}
}
