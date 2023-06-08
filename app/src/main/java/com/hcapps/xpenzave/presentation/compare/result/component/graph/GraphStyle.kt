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
    val secondBarColor: Color,
    val barWidth: Dp = 8.dp,
    val selectedIndicatorColor: Color,
    val selectorBorderTint: Color,
    val spaceBetweenGraphBar: Dp = 8.dp,
    val textStyle: TextStyle,
    val barSelectorWidth: Dp = 48.dp
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
            textStyle: TextStyle = MaterialTheme.typography.labelLarge,
            barSelectorWidth: Dp = 48.dp,
            secondBarColor: Color = MaterialTheme.colorScheme.inversePrimary
        ) = GraphStyle(
            barSize = barSize,
            barColor = barColor,
            barWidth = barWidth,
            selectedIndicatorColor = selectedIndicatorColor,
            selectorBorderTint = selectorBorderTint,
            spaceBetweenGraphBar = spaceBetweenGraphBar,
            textStyle = textStyle,
            barSelectorWidth = barSelectorWidth,
            secondBarColor = secondBarColor
        )
    }
}