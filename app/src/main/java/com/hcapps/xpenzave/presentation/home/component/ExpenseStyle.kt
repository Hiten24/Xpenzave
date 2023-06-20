package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ExpenseItemStyle(
    val iconColor: Color,
    val cardBackground: Color,
    val costTextColor: Color,
    val costTextWeight: FontWeight?,
    val elevation: Dp
) {
    companion object {
        @Composable
        fun defaultExpenseItemStyle(): ExpenseItemStyle {
            return ExpenseItemStyle(
                iconColor = MaterialTheme.colorScheme.primary,
                cardBackground = MaterialTheme.colorScheme.surface,
                costTextColor = MaterialTheme.colorScheme.primary,
                elevation = 1.dp,
                costTextWeight = MaterialTheme.typography.labelLarge.fontWeight
            )
        }
    }
}

data class ExpenseDateHeaderStyle(
    val headerBorderColor: Color,
    val dayTextColor: Color,
    val dateTextColor: Color,
    val headerBackgroundColor: Color,
    val cardShape: CornerBasedShape,
    val dateFontWeight: FontWeight?,
    val borderWidth: Dp = 1.dp
) {
    companion object {
        @Composable
        fun defaultExpenseDateHeaderStyle() = ExpenseDateHeaderStyle(
            headerBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            dayTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            dateTextColor = MaterialTheme.colorScheme.primary,
            headerBackgroundColor = MaterialTheme.colorScheme.surface,
            cardShape = Shapes().small,
            dateFontWeight = FontWeight.Medium
        )
    }
}
