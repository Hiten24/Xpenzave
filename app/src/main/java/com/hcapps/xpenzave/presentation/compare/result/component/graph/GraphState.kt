package com.hcapps.xpenzave.presentation.compare.result.component.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberGraphState() = remember {
    GraphState()
}

class GraphState {
    var selectedGraphBar by mutableStateOf(0)
}