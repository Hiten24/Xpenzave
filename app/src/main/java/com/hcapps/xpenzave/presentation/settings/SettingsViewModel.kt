package com.hcapps.xpenzave.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.domain.usecase.auth.ChangePasswordUseCase
import com.hcapps.xpenzave.domain.usecase.auth.LogOutUseCase
import com.hcapps.xpenzave.presentation.core.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    dataStore: DataStoreService,
    private val logOutUseCase: LogOutUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
): ViewModel() {

    private val _state = mutableStateOf(SettingsState())
    var state = _state

    private val user = dataStore.getUserFlow()

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                email = user.first().email ?: "",
//                currencyCode = user.currencyCode ?: ""
            )
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OldPasswordChanged -> {
                _state.value = state.value.copy(oldPassword = event.password)
            }
            is SettingsEvent.NewPasswordChanged -> {
                _state.value = state.value.copy(newPassword = event.password)
            }
            is SettingsEvent.ChangePassword -> {
                changePassword(state.value.oldPassword, state.value.newPassword) {
                    event.onSuccess()
                }
            }
            is SettingsEvent.LogOut -> {
                logOut(event.onSuccess)
            }
        }
    }

    private fun changePassword(old: String, new: String, onSuccess: () -> Unit) = viewModelScope.launch {
        if (!validate(old, new)) return@launch
        try {
            changePasswordUseCase(old, new)
            onSuccess()
        } catch (e: Exception) {
            when (e.message) {
                "Invalid credentials. Please check the email and password." -> {
                    _state.value = state.value.copy(oldPasswordError = "Invalid Password")
                }
            }
            Timber.e(e)
        }
    }

    private fun validate(oldPassword: String, newPassword: String): Boolean {
        when {
            oldPassword.length < 8 -> {
                _state.value = state.value.copy(oldPasswordError = "Passwords must be at least 8 char long.")
            }
            newPassword.length < 8 -> {
                _state.value = state.value.copy(newPasswordError = "Passwords must be at least 8 char long.")
            }
        }
        return oldPassword.length >= 8 && newPassword.length >= 8
    }

    private fun logOut(onSuccess: () -> Unit) = viewModelScope.launch {
        _state.value = state.value.copy(logOutLoading = true)
        try {
            logOutUseCase()
            onSuccess()
            _state.value = state.value.copy(logOutLoading = false)
        } catch (e: Exception) {
            _state.value = state.value.copy(logOutLoading = false)
            Timber.e(e)
        }
    }

    /*fun setCurrencyPreference(currencyCode: String) = viewModelScope.launch {
        dataStore.saveUser(user.first().copy(currencyCode = currencyCode))
    }*/

}