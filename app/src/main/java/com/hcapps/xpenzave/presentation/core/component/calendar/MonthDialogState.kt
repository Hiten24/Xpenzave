package com.hcapps.xpenzave.presentation.core.component.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MonthDialogState {

    private val dialogOpened = mutableStateOf(false)

    fun show() {
        dialogOpened.value = true
    }

    fun dismiss() {
        dialogOpened.value = false
    }

    fun opened(): Boolean {
        return dialogOpened.value
    }
}

@Composable
fun rememberMonthState() = remember { mutableStateOf(MonthDialogState()) }