package com.hcapps.xpenzave.domain.model.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.DragIndicator
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector
import com.hcapps.xpenzave.BuildConfig

data class Category(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val isSelected: Boolean
) {
    companion object {
        fun dummies() = listOf(
            Category(
                name = "Bills",
                icon = Icons.Outlined.Receipt,
                isSelected = true,
                id = BuildConfig.CATEGORIES.getValue("Bills")
            ),
            Category(
                name = "Food",
                icon = Icons.Outlined.LocalPizza,
                isSelected = false,
                id = BuildConfig.CATEGORIES.getValue("Food")
            ),
            Category(
                name = "Clothes",
                icon = Icons.Outlined.ShoppingBag,
                isSelected = false,
                id = BuildConfig.CATEGORIES.getValue("Clothes")
            ),
            Category(
                name = "Transport",
                icon = Icons.Outlined.DirectionsCar,
                isSelected = false,
                id = BuildConfig.CATEGORIES.getValue("Transport")
            ),
            Category(
                name = "Fun",
                icon = Icons.Outlined.Mood,
                isSelected = false,
                id = BuildConfig.CATEGORIES.getValue("Fun")
            ),
            Category(
                name = "Other",
                icon = Icons.Outlined.DragIndicator,
                isSelected = false,
                id = BuildConfig.CATEGORIES.getValue("Other")
            )
        )
    }
}
