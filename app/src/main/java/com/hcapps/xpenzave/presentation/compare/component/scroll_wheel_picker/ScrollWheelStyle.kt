package com.hcapps.xpenzave.presentation.compare.component.scroll_wheel_picker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScrollWheelStyle(
    val textStyle: TextStyle,
    val textColor: Color,
    val indicatorColor: Color,
    val spaceBetweenItem: Dp,
    val indicatorSize: Dp,
) {
    companion object {
        @Composable
        fun defaultWheelPickerStyle(
            textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
            textColor: Color = MaterialTheme.colorScheme.primary,
            indicatorColor: Color = MaterialTheme.colorScheme.primary,
            spaceBetweenItem: Dp = 8.dp,
            indicatorSize: Dp = 12.dp
        ) = ScrollWheelStyle(
            textStyle, textColor, indicatorColor, spaceBetweenItem, indicatorSize
        )
    }
}