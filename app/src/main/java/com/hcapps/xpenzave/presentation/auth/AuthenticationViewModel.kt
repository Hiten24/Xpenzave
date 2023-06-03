package com.hcapps.xpenzave.presentation.auth

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.AuthRepository
import com.hcapps.xpenzave.util.ResponseState
import com.hcapps.xpenzave.util.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    var authScreenState = mutableStateOf(1)
        private set

    var emailState = mutableStateOf("")
        private set

    var passwordState = mutableStateOf("")
        private set

    fun registerUser(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) = viewModelScope.launch {
        if (!validateFields()) {
            onError(Exception("Enter all require details to register."))
            return@launch
        }
        val email = emailState.value
        val password = passwordState.value
        when (val response = authRepository.createAccountWithCredentials(email, password)) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SettingsDataStore.SETTINGS_IS_LOGGED_IN_KEY, true)
                onSuccess()
            }
            is ResponseState.Error -> onError(response.error)
            else -> Unit
        }
    }

    fun login(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) = viewModelScope.launch {
        if (!validateFields()) {
            onError(Exception("Enter all require details to login."))
            return@launch
        }
        val email = emailState.value
        val password = passwordState.value
        when (val response = authRepository.loginWithCredentials(email, password)) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SettingsDataStore.SETTINGS_IS_LOGGED_IN_KEY, true)
                onSuccess()
            }
            is ResponseState.Error -> onError(response.error)
            else -> Unit
        }
    }

    fun loginWithOath2(
        activity: ComponentActivity,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
        provider: String
    ) = viewModelScope.launch {
        when (val response = authRepository.authenticateWithOauth2(activity, provider)) {
            is ResponseState.Success -> onSuccess()
            is ResponseState.Error -> onError(response.error)
            else -> Unit
        }
    }

    private fun validateFields(): Boolean {
        return (emailState.value.isEmpty() || passwordState.value.isEmpty()).not()
    }

}