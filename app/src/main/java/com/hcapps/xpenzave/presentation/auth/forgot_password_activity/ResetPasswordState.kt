package com.hcapps.xpenzave.presentation.auth.forgot_password_activity

import com.hcapps.xpenzave.presentation.auth.event.PasswordState

data class ResetPasswordState(
    val userId: String? = null,
    val secret: String? = null,
    val expire: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val loading: Boolean = false,
    val passwordState: PasswordState? = null
)
