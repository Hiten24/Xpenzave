package com.hcapps.xpenzave.presentation.settings

import com.hcapps.xpenzave.presentation.core.AlertDialogState

data class SettingsState(
    val email: String = "",
    val currencyCode: String = "",
    val logOutLoading: Boolean = false,
    val oldPassword: String = "",
    val newPassword: String = "",
    val oldPasswordError: String? = null,
    val newPasswordError: String? = null,
    val changePasswordDialog: AlertDialogState = AlertDialogState()
)