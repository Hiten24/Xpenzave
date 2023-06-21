package com.hcapps.xpenzave.presentation.auth.forgot_password

data class ForgotPasswordState(
    val email: String = "",
    val emailError: String? = "",
    val loading: Boolean = false,
    val confirmationDialogState: Boolean = false
)
