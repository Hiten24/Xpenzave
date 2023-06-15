package com.hcapps.xpenzave.presentation.auth.event

sealed class AuthUiEventFlow {
    object OAuth2Success: AuthUiEventFlow()
    data class Message(val msg: String): AuthUiEventFlow()
}
