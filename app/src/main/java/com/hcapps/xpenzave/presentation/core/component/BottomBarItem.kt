package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val icon: ImageVector,
    val label: String,
    val route: String,
    val description: String? = null
)
