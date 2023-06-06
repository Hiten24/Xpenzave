package com.hcapps.xpenzave.presentation.add_expense

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryData(
    val name: String,
    val icon: ImageVector,
    val isSelected: Boolean
) {
    companion object {
        fun dummies() = listOf(
            CategoryData(
                name = "Bills",
                icon = Icons.Outlined.Receipt,
                isSelected = true
            ),
            CategoryData(
                name = "Shopping",
                icon = Icons.Outlined.ShoppingBag,
                isSelected = false
            ),
            CategoryData(
                name = "Food",
                icon = Icons.Outlined.Restaurant,
                isSelected = false
            ),
            CategoryData(
                name = "Transport",
                icon = Icons.Outlined.DirectionsCar,
                isSelected = false
            ),
            CategoryData(
                name = "Investment",
                icon = Icons.Outlined.Savings,
                isSelected = false
            ),
            CategoryData(
                name = "Medication",
                icon = Icons.Outlined.Medication,
                isSelected = false
            )
        )
    }
}
