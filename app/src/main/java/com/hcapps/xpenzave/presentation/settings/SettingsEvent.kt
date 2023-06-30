package com.hcapps.xpenzave.presentation.settings

sealed class SettingsEvent {
    data class LogOut(val onSuccess: () -> Unit): SettingsEvent()
}
