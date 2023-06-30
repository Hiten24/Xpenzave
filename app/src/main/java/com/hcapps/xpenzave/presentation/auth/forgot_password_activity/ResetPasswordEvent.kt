package com.hcapps.xpenzave.presentation.auth.forgot_password_activity

sealed class ResetPasswordEvent {
    data class IntentData(val userId: String?, val secret: String?, val expire: String?): ResetPasswordEvent()
    data class PasswordChanged(val password: String): ResetPasswordEvent()
    data class ConfirmPasswordChanged(val password: String): ResetPasswordEvent()
    data class OnPasswordChanged(val onSuccess: () -> Unit, val onError: (String) -> Unit): ResetPasswordEvent()
}
