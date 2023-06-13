package com.hcapps.xpenzave.presentation

import java.time.DayOfWeek
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

fun Month.defaultDisplayName(): String = this.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

fun DayOfWeek.defaultDisplayName(): String = this.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

fun ordinalDayOfMonth(day: Int): String {
    return "${day}${ordinalIndicator(day)}"
}

private fun ordinalIndicator(day: Int): String {
    val exceptNumber = listOf(11, 12, 13)
    return when  {
        day % 10 == 1 && !exceptNumber.contains(day) -> "st"
        day % 10 == 2 && !exceptNumber.contains(day) -> "nd"
        day % 10 == 3 && !exceptNumber.contains(day) -> "rd"
        else -> "th"
    }
}