package com.hcapps.xpenzave.presentation.core.component.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class SnackbarVisualsWithError(
    override val message: String,
    val isError: Boolean
): SnackbarVisuals {

    override val actionLabel: String
        get() = if (isError) "Error" else "OK"
    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short
    override val withDismissAction: Boolean
        get() = false
}