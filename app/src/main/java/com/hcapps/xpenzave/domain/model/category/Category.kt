package com.hcapps.xpenzave.domain.model.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.DragIndicator
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

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
                id = "648432cbef8b35904dc3"
            ),
            Category(
                name = "Food",
                icon = Icons.Outlined.LocalPizza,
                isSelected = false,
                id = "648464160f6d09d88ebf"
            ),
            Category(
                name = "Clothes",
                icon = Icons.Outlined.ShoppingBag,
                isSelected = false,
                id = "6484658d615d05b18e8a"
            ),
            Category(
                name = "Transport",
                icon = Icons.Outlined.DirectionsCar,
                isSelected = false,
                id = "648465a1ee06a4b50458"
            ),
            Category(
                name = "Fun",
                icon = Icons.Outlined.Mood,
                isSelected = false,
                id = "648465b06ce72f97fec5"
            ),
            Category(
                name = "Other",
                icon = Icons.Outlined.DragIndicator,
                isSelected = false,
                id = "648465c07a638335c0aa"
            )
        )
    }
}
