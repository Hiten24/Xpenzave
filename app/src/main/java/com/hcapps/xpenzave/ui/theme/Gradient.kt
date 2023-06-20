package com.hcapps.xpenzave.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

@Composable
fun primaryGradient(): Brush {
    return Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    )
}