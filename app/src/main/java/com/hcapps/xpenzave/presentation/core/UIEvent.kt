package com.hcapps.xpenzave.presentation.core

sealed class UIEvent {
    data class Error(val error: Throwable): UIEvent()
    data class ShowMessage(val message: String): UIEvent()
    data class Loading(val loading: Boolean): UIEvent()
}
