package com.hcapps.xpenzave.presentation.change_password

sealed class ChangePasswordEvent {
    data class OldPasswordChanged(val password: String): ChangePasswordEvent()
    data class NewPasswordChanged(val password: String): ChangePasswordEvent()
    data class OnPasswordChanged(val onSuccess: () -> Unit): ChangePasswordEvent()
}
