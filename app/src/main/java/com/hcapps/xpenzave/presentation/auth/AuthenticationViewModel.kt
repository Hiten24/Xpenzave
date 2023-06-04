package com.hcapps.xpenzave.presentation.auth

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.AuthRepository
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.AuthScreenState
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.util.ResponseState
import com.hcapps.xpenzave.util.SettingsDataStore
import com.hcapps.xpenzave.util.SettingsDataStore.Companion.SETTINGS_IS_LOGGED_IN_KEY
import com.hcapps.xpenzave.util.SettingsDataStore.Companion.USER_EMAIL_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    var authScreenState = mutableStateOf(AuthScreenState())
        private set

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private fun clearFields() {
        authScreenState.value = authScreenState.value.copy(email = "")
        authScreenState.value = authScreenState.value.copy(password = "")
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                authScreenState.value = authScreenState.value.copy(email = event.email)
            }
            is AuthEvent.PasswordChanged -> {
                authScreenState.value = authScreenState.value.copy(password = event.password)
            }
            is AuthEvent.SwitchAuthScreen -> {
                clearFields()
                authScreenState.value = authScreenState.value.copy(authState = event.screen)
            }
        }
    }

    fun registerUser(
        onSuccess: () -> Unit
    ) = viewModelScope.launch {
        if (!validateFields()) {
            onError(Exception("Enter all require details to register."))
            return@launch
        }
        showLoading(true)
        val email = authScreenState.value.email
        val password = authScreenState.value.password
        when (val response = authRepository.createAccountWithCredentials(email, password)) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SETTINGS_IS_LOGGED_IN_KEY, true)
                settingsDataStore.saveString(USER_EMAIL_KEY, response.data.email)
                showLoading(false)
                onSuccess()
            }
            is ResponseState.Error -> {
                showLoading(false)
                onError(response.error)
            }
            else -> Unit
        }
    }

    fun login(
        onSuccess: () -> Unit
    ) = viewModelScope.launch {
        if (!validateFields()) {
            onError(Exception("Enter all require details to login."))
            return@launch
        }
        showLoading(true)
        val email = authScreenState.value.email
        val password = authScreenState.value.password
        when (val response = authRepository.loginWithCredentials(email, password)) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SETTINGS_IS_LOGGED_IN_KEY, true)
                settingsDataStore.saveString(USER_EMAIL_KEY, email)
                showLoading(false)
                onSuccess()
            }
            is ResponseState.Error -> {
                showLoading(false)
                onError(response.error)
            }
            else -> Unit
        }
    }

    fun loginWithOath2(
        activity: ComponentActivity,
        onSuccess: () -> Unit,
        provider: String
    ) = viewModelScope.launch {
        when (val response = authRepository.authenticateWithOauth2(activity, provider)) {
            is ResponseState.Success -> onSuccess()
            is ResponseState.Error -> onError(response.error)
            else -> Unit
        }
    }

    private fun validateFields(): Boolean {
        return (authScreenState.value.email.isEmpty() || authScreenState.value.password.isEmpty()).not()
    }

    private fun showLoading(loading: Boolean) {
        authScreenState.value = authScreenState.value.copy(loading = loading)
    }

    private fun onError(error: Throwable) = viewModelScope.launch {
        _uiEventFlow.emit(UIEvent.Error(error))
    }

}