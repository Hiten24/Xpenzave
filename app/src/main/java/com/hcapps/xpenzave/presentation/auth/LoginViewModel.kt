package com.hcapps.xpenzave.presentation.auth

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.domain.usecase.auth.LoginUseCase
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.ConfirmPasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.EmailChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.Login
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.PasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthScreenState
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _state = mutableStateOf(AuthScreenState())
    val state = _state

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

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
                login(event.onSuccess)
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

    private fun login(onSuccess: () -> Unit) = viewModelScope.launch {
        _state.value = state.value.copy(loading = true)
        try {
            loginUseCase(state.value.email, state.value.password)
            onSuccess()
        } catch (e: Exception) {
            if (e is IOException) {
                _uiEvent.emit(UIEvent.Error(UiText.StringResource(R.string.internet_error_msg)))
            } else if (e.message == "Invalid credentials. Please check the email and password.") {
                _state.value = state.value.copy(passwordError = "Invalid credentials.")
            } else { Timber.e(e) }
        }
        _state.value = state.value.copy(loading = false)
    }

}