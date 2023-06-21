package com.hcapps.xpenzave.presentation.auth.forgot_password

sealed class ForgotPasswordEvent {
    data class EmailChange(val email: String): ForgotPasswordEvent()
    data class ContinueButton(val onSuccess: () -> Unit): ForgotPasswordEvent()
}
