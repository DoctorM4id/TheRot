@file:Suppress("unused")
package dev.doctorm4id.rot.util

import net.minecraft.world.level.Level
import kotlin.math.abs

object TickUtil {

	val TICKS_PER_SECOND: Int = 20
	val TICKS_PER_MINUTE: Int = TICKS_PER_SECOND * 60
	val TICKS_PER_HOUR: Int = TICKS_PER_MINUTE * 60
	val TICKS_PER_DAY: Int = TICKS_PER_HOUR * 24
	val TICKS_PER_WEEK: Int = TICKS_PER_DAY * 7
	val TICKS_PER_MONTH: Int = TICKS_PER_DAY * 30
	val TICKS_PER_YEAR: Int = TICKS_PER_DAY * 365

	fun convertSecondsToTicks(seconds: Float): Int {
		return (seconds * TICKS_PER_SECOND).toInt()
	}

	fun convertTicksToSeconds(ticks: Long): Long {
		return ticks / TICKS_PER_SECOND
	}

	fun convertSecondsToTicks(seconds: Int): Int {
		return seconds * TICKS_PER_SECOND
	}

	fun convertMinutesToTicks(minutes: Int): Int {
		return minutes * TICKS_PER_MINUTE
	}

	fun convertTicksToMinutes(ticks: Long): Long {
		return ticks / TICKS_PER_MINUTE
	}

	fun convertHoursToTicks(hours: Int): Int {
		return hours * TICKS_PER_HOUR
	}

	fun convertDaysToTicks(days: Int): Int {
		return days * TICKS_PER_DAY
	}

	fun convertWeeksToTicks(weeks: Int): Int {
		return weeks * TICKS_PER_WEEK
	}

	fun convertMonthsToTicks(months: Int): Int {
		return months * TICKS_PER_MONTH
	}

	fun convertYearsToTicks(years: Int): Int {
		return years * TICKS_PER_YEAR
	}

	fun hasTicksPassed(lastTickTime: Long, currentTickTime: Long, ticks: Long): Boolean {
		if (lastTickTime == 0L) return true

		return abs(currentTickTime - lastTickTime) >= ticks
	}

	fun hasTicksPassed(lastTickTime: Long, level: Level, ticks: Long): Boolean {
		return hasTicksPassed(lastTickTime, level.gameTime, ticks)
	}
}
