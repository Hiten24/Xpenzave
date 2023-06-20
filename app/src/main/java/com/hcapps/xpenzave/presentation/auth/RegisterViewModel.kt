package com.hcapps.xpenzave.presentation.auth

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.usecase.auth.RegisterUseCase
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.ConfirmPasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.EmailChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.PasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.Register
import com.hcapps.xpenzave.presentation.auth.event.AuthScreenState
import com.hcapps.xpenzave.presentation.auth.event.PasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
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
                val pass = event.password
                val charLimit = pass.length in 8..20
                val lowerCase = Regex("[a-z]").containsMatchIn(pass)
                val upperCase = Regex("[A-Z]").containsMatchIn(pass)
                val specialChar = Regex("[1-9$!#&@?=_]").containsMatchIn(pass)
                _state.value = state.value.copy(createPasswordState = PasswordState(
                    shouldBeMin8Max20Char = charLimit,
                    shouldHaveALowerCase = lowerCase,
                    shouldHaveAUpperCase = upperCase,
                    shouldHaveANumberOrAcceptableCharacter = specialChar
                ))
            }
            is ConfirmPasswordChanged -> {
                _state.value = state.value.copy(confirmPassword = event.password)
            }
            is Register -> {
                if (validate().not()) {
                    _state.value = state.value.copy(loading = false)
                    return
                }
                register()
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

    private fun register() = viewModelScope.launch {
        _state.value = state.value.copy(loading = true)
        try {
            registerUseCase(state.value.email, state.value.password)
        } catch (e: Exception) {
            Timber.e(e)
        }
        _state.value = state.value.copy(loading = false)
    }

}