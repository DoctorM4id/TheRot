package dev.doctorm4id.rot.content.item

import dev.doctorm4id.rot.systems.InfestationSystem
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class InfestWand(properties : Properties) : Item(properties) {

	override fun useOn(ctx: UseOnContext): InteractionResult {
		val level = ctx.level
		if (level is ServerLevel) {
			val targetPos: BlockPos = ctx.clickedPos.relative(ctx.clickedFace)

			InfestationSystem.infestPosition(level, targetPos)

			return InteractionResult.SUCCESS
		}

		return InteractionResult.SUCCESS
	}
}
