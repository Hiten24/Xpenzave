package com.hcapps.xpenzave.presentation.add_expense

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val icon: ImageVector,
    var isSelected: Boolean
) {
    companion object {
        fun dummies() = listOf(
            Category(
                name = "Bills",
                icon = Icons.Outlined.Receipt,
                isSelected = false
            ),
            Category(
                name = "Shopping",
                icon = Icons.Outlined.ShoppingBag,
                isSelected = false
            ),
            Category(
                name = "Food",
                icon = Icons.Outlined.Restaurant,
                isSelected = false
            ),
            Category(
                name = "Transport",
                icon = Icons.Outlined.DirectionsCar,
                isSelected = false
            ),
            Category(
                name = "Investment",
                icon = Icons.Outlined.Savings,
                isSelected = false
            ),
            Category(
                name = "Medication",
                icon = Icons.Outlined.Medication,
                isSelected = false
            )
        )
    }
}
