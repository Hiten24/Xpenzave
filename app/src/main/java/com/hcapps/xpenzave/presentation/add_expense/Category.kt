package com.hcapps.xpenzave.presentation.add_expense

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.DragIndicator
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val icon: ImageVector,
    val isSelected: Boolean
) {
    companion object {
        fun dummies() = listOf(
            Category(
                name = "Bills",
                icon = Icons.Outlined.Receipt,
                isSelected = true
            ),
            Category(
                name = "Shopping",
                icon = Icons.Outlined.ShoppingBag,
                isSelected = false
            ),
            Category(
                name = "Clothes",
                icon = Icons.Outlined.Restaurant,
                isSelected = false
            ),
            Category(
                name = "Transport",
                icon = Icons.Outlined.DirectionsCar,
                isSelected = false
            ),
            Category(
                name = "Fun",
                icon = Icons.Outlined.Mood,
                isSelected = false
            ),
            Category(
                name = "Other",
                icon = Icons.Outlined.DragIndicator,
                isSelected = false
            )
        )
    }
}
