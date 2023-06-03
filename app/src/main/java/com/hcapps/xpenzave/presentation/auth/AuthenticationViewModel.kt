package com.hcapps.xpenzave.presentation.auth

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.AuthRepository
import com.hcapps.xpenzave.util.ResponseState
import com.hcapps.xpenzave.util.SettingsDataStore
import com.hcapps.xpenzave.util.SettingsDataStore.Companion.SETTINGS_IS_LOGGED_IN_KEY
import com.hcapps.xpenzave.util.SettingsDataStore.Companion.USER_EMAIL_KEY
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

    var loadingState = mutableStateOf(false)
        private set

    fun clearFields() {
        emailState.value = ""
        passwordState.value = ""
    }

    fun registerUser(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) = viewModelScope.launch {
        if (!validateFields()) {
            onError(Exception("Enter all require details to register."))
            return@launch
        }
        loadingState.value = true
        val email = emailState.value
        val password = passwordState.value
        when (val response = authRepository.createAccountWithCredentials(email, password)) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SETTINGS_IS_LOGGED_IN_KEY, true)
                settingsDataStore.saveString(USER_EMAIL_KEY, response.data.email)
                loadingState.value = false
                onSuccess()
            }
            is ResponseState.Error -> {
                loadingState.value = false
                onError(response.error)
            }
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
        loadingState.value = true
        val email = emailState.value
        val password = passwordState.value
        when (val response = authRepository.loginWithCredentials(email, password)) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SETTINGS_IS_LOGGED_IN_KEY, true)
                settingsDataStore.saveString(USER_EMAIL_KEY, email)
                loadingState.value = false
                onSuccess()
            }
            is ResponseState.Error -> {
                loadingState.value = false
                onError(response.error)
            }
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