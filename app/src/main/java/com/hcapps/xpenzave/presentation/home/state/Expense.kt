package com.hcapps.xpenzave.presentation.home.state

import androidx.compose.ui.graphics.vector.ImageVector

data class Expense(
    val icon: ImageVector,
    val title: String,
    val value: Int,
    val symbol: String
)
