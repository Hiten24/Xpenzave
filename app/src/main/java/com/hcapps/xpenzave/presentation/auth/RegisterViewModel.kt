package com.hcapps.xpenzave.presentation.auth

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.domain.usecase.auth.RegisterUseCase
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.ConfirmPasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.EmailChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.PasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.Register
import com.hcapps.xpenzave.presentation.auth.event.AuthScreenState
import com.hcapps.xpenzave.presentation.auth.event.PasswordState.Companion.checkAllRule
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UIEvent.Error
import com.hcapps.xpenzave.presentation.core.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
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
                val pass = event.password
                _state.value = state.value.copy(createPasswordState = checkAllRule(pass))
            }
            is ConfirmPasswordChanged -> {
                _state.value = state.value.copy(confirmPassword = event.password)
            }
            is Register -> {
                if (validate().not()) {
                    _state.value = state.value.copy(loading = false)
                    return
                }
                register(event.onSuccess)
            }
            else -> {}
        }
    }

    private fun validate(): Boolean {
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
        val passwordValid = state.value.password.length >= 8
        val passwordMatchValid = state.value.password == state.value.confirmPassword
        val passRule = state.value.createPasswordState
        val passwordRules = passRule?.shouldBeMin8Max20Char == true && passRule.shouldHaveAUpperCase && passRule.shouldHaveALowerCase && passRule.shouldHaveANumberOrAcceptableCharacter
        if (!emailValid) {
            _state.value = state.value.copy(emailError = "Enter Valid Email address.")
        } else if (!passwordValid) {
            _state.value = state.value.copy(passwordError = "Password must be at least 8 chars long.")
        } else if (!passwordMatchValid) {
            _state.value = state.value.copy(confirmPasswordError = "Password must be same.")
        } else if (!passwordRules) {
            _state.value = state.value.copy(passwordError = "Password should contain below:")
        }
        return emailValid && passwordMatchValid && passwordRules
    }

    private fun register(onSuccess: () -> Unit) = viewModelScope.launch {
        _state.value = state.value.copy(loading = true)
        try {
            registerUseCase(state.value.email, state.value.password)
            onSuccess()
        } catch (e: Exception) {
            if (e is IOException) {
                _uiEvent.emit(Error(UiText.StringResource(R.string.internet_error_msg)))
            } else { Timber.e(e) }
        }
        _state.value = state.value.copy(loading = false)
    }

}