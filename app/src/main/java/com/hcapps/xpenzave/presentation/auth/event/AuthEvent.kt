package com.hcapps.xpenzave.presentation.auth.event

sealed class AuthEvent {
    data class EmailChanged(val email: String): AuthEvent()
    data class PasswordChanged(val password: String): AuthEvent()
    data class SetPasswordFocusChanged(val isFocused: Boolean): AuthEvent()
    data class ConfirmPasswordChanged(val password: String): AuthEvent()
    // switch between login and register screen
    data class SwitchAuthScreen(val screen: Int): AuthEvent()
    data class Login(val onSuccess: () -> Unit): AuthEvent()
    data class Register(val onSuccess: () -> Unit): AuthEvent()
}
