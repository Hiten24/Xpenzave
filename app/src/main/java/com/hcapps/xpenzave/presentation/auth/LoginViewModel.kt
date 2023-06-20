package com.hcapps.xpenzave.presentation.auth

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.usecase.auth.LoginUseCase
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.ConfirmPasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.EmailChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.Login
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.PasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _state = mutableStateOf(AuthScreenState())
    val state = _state

    fun onEvent(event: AuthEvent) {
        when (event) {
            is EmailChanged -> {
                _state.value = state.value.copy(email = event.email, emailError = null)
            }
            is PasswordChanged -> {
                _state.value = state.value.copy(password = event.password, passwordError = null)
            }
            is ConfirmPasswordChanged -> {
                _state.value = state.value.copy(confirmPassword = event.password)
            }
            is Login -> {
                if (validate().not()) {
                    _state.value = state.value.copy(loading = false)
                    return
                }
                login()
            }
            else -> {}
        }
    }

    private fun validate(): Boolean {
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
        val passwordValid = state.value.password.length >= 8
        if (!emailValid) {
            _state.value = state.value.copy(emailError = "Enter Valid Email address.")
        } else if (!passwordValid) {
            _state.value = state.value.copy(passwordError = "Invalid credentials.")
        }
        return emailValid && passwordValid
    }

    private fun login() = viewModelScope.launch {
        _state.value = state.value.copy(loading = true)
        try {
            loginUseCase(state.value.email, state.value.password)
        } catch (e: Exception) {
            if (e.message == "Invalid credentials. Please check the email and password.") {
                _state.value = state.value.copy(passwordError = "Invalid credentials.")
            }
            Timber.e(e)
        }
        _state.value = state.value.copy(loading = false)
    }

}