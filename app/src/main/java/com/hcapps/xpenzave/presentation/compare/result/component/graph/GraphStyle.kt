package com.hcapps.xpenzave.presentation.compare.result.component.graph

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class GraphStyle(
    val barSize: Dp = 120.dp,
    val barColor: Color,
    val barWidth: Dp = 8.dp,
    val selectedIndicatorColor: Color,
    val selectorBorderTint: Color,
    val spaceBetweenGraphBar: Dp = 8.dp,
    val textStyle: TextStyle
) {
    companion object {
        @Composable
        fun defaultGraphStyle(
            barSize: Dp = 120.dp,
            barColor: Color = MaterialTheme.colorScheme.primary,
            barWidth: Dp = 8.dp,
            spaceBetweenGraphBar: Dp = 8.dp,
            selectedIndicatorColor: Color = MaterialTheme.colorScheme.surface,
            selectorBorderTint: Color = MaterialTheme.colorScheme.primary,
            textStyle: TextStyle = MaterialTheme.typography.labelLarge
        ) = GraphStyle(barSize, barColor, barWidth, selectedIndicatorColor, selectorBorderTint, spaceBetweenGraphBar, textStyle)
    }
}