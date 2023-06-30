package com.hcapps.xpenzave.presentation.core

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SharedFlow<UIEvent>.UiEventReceiver() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = this) {
        this@UiEventReceiver.collectLatest {
            when (it) {
                is UIEvent.ShowMessage -> scope.launch {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UIEvent.Error -> scope.launch {
                    Toast.makeText(context, it.error.asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}