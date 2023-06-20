package com.hcapps.xpenzave.presentation.settings

data class SettingsState(
    val email: String = "",
    val currencyCode: String = "",
    val logOutLoading: Boolean = false
)