package com.hcapps.xpenzave.presentation.core

sealed class UIEvent {
    data class Error(val error: UiText): UIEvent()
    data class ShowMessage(val message: String): UIEvent()
}
