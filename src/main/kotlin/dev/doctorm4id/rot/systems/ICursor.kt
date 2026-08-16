package dev.doctorm4id.rot.systems

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import java.util.UUID
import kotlin.uuid.Uuid

interface ICursor {

	fun tick()

	fun getID(): UUID

	fun getWorld(): Level
	fun getPos(): BlockPos?
	fun moveTo(x: Int, y: Int, z: Int)

	fun setState(state: VirtualCursor.State)

	fun isExpired(): Boolean
	fun setExpired()
}
