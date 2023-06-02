package com.hcapps.xpenzave.ui.theme

import androidx.compose.ui.graphics.Color

val color_primary = Color(0xFF214FF1)
val color_secondary = Color(0xFF007AFF)
val color_tertiary = Color(0xFF9BA5F8)
val color_optional = Color(0xFF3BD0FF)
val dark_background = Color(0xFF383A42)
val light_background = Color(0xFFF8F8F8)

val color_info_1 = Color(0xFFFF4F49)
val color_info_2 = Color(0xFFFF8B4A)

object XpenzaveColor {
    val colorSchema = ColorSchema(
        primary = color_primary,
        secondary = color_secondary,
        tertiary = color_tertiary,
        optional = color_optional,
        darkBackground = dark_background,
        lightBackground = light_background,
        colorInfoStart = color_info_1,
        colorInfoEnd = color_info_2
    )
}

data class ColorSchema(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val optional: Color,
    val darkBackground: Color,
    val lightBackground: Color,
    val colorInfoStart: Color,
    val colorInfoEnd: Color
)