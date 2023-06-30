package com.hcapps.xpenzave.presentation.change_password

import com.hcapps.xpenzave.presentation.auth.event.PasswordState

data class ChangePasswordState(
    val oldPassword: String = "",
    val oldPasswordError: String? = null,
    val newPassword: String = "",
    val newPasswordError: String? = null,
    val passwordState: PasswordState? = null,
    val loading: Boolean = false
)
