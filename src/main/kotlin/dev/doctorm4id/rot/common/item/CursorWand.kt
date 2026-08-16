package dev.doctorm4id.rot.common.item

import dev.doctorm4id.rot.systems.CursorManager
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class CursorWand(properties : Properties) : Item(properties) {
	override fun useOn(ctx: UseOnContext): InteractionResult {
		val level = ctx.level
		if (level is ServerLevel) {
			val spawnPos: BlockPos = ctx.clickedPos.relative(ctx.clickedFace)

			CursorManager.createSurfaceInfectorVirtualCursor(level, spawnPos)

			return InteractionResult.SUCCESS
		}

		return InteractionResult.SUCCESS
	}
}
