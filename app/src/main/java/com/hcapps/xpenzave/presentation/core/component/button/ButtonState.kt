package com.hcapps.xpenzave.presentation.core.component.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class ButtonState(
    val enabled: Boolean = true,
    val loading: Boolean = false
)

@Composable
fun rememberButtonState() = remember { ButtonState() }
