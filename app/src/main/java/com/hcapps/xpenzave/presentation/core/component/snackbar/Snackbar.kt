package com.hcapps.xpenzave.presentation.core.component.snackbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SnackbarData.XpenzaveSnackbar() {
    val isError = (visuals as? SnackbarVisualsWithError)?.isError ?: false
    val containerColor = if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.inverseSurface
    val contentColor = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.inverseOnSurface
    val borderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.inverseSurface
    Snackbar(
        modifier = Modifier.padding(12.dp).border(2.dp, borderColor, MaterialTheme.shapes.small),
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        Text(visuals.message)
   }
}