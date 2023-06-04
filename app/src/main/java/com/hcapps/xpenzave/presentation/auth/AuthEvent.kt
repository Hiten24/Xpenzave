package com.hcapps.xpenzave.presentation.auth

sealed class AuthEvent {
    data class EmailChanged(val email: String): AuthEvent()
    data class PasswordChanged(val password: String): AuthEvent()
    // switch between login and register screen
    data class SwitchAuthScreen(val screen: Int): AuthEvent()
}
