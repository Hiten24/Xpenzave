package com.hcapps.xpenzave.presentation.settings

sealed class SettingsEvent {
    data class OldPasswordChanged(val password: String): SettingsEvent()
    data class NewPasswordChanged(val password: String): SettingsEvent()
    data class LogOut(val onSuccess: () -> Unit): SettingsEvent()
    data class ChangePassword(val onSuccess: () -> Unit): SettingsEvent()
}
