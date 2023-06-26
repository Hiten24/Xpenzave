package com.hcapps.xpenzave.presentation.auth.forgot_password_activity

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.presentation.auth.event.PasswordState
import com.hcapps.xpenzave.presentation.auth.event.PasswordState.Companion.shouldBeMin8Max20Char
import com.hcapps.xpenzave.presentation.auth.event.PasswordState.Companion.shouldHaveALowerCase
import com.hcapps.xpenzave.presentation.auth.event.PasswordState.Companion.shouldHaveANumberOrAcceptableCharacter
import com.hcapps.xpenzave.presentation.auth.event.PasswordState.Companion.shouldHaveAUpperCase
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.ResetPasswordEvent.ConfirmPasswordChanged
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.ResetPasswordEvent.IntentData
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.ResetPasswordEvent.OnPasswordChanged
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.ResetPasswordEvent.PasswordChanged
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UIEvent.Error
import com.hcapps.xpenzave.presentation.core.UiText.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RestPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _state = mutableStateOf(ResetPasswordState())
    val state = _state

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is IntentData -> {
                _state.value = state.value.copy(userId = event.userId, secret = event.secret, expire = event.expire)
            }
            is PasswordChanged -> {
                _state.value = state.value.copy(password = event.password, passwordError = null)
                val pass = state.value.password
                    _state.value = state.value.copy(passwordState = PasswordState(
                    shouldBeMin8Max20Char = shouldBeMin8Max20Char(pass),
                    shouldHaveALowerCase = shouldHaveALowerCase(pass),
                    shouldHaveAUpperCase = shouldHaveAUpperCase(pass),
                    shouldHaveANumberOrAcceptableCharacter = shouldHaveANumberOrAcceptableCharacter(pass)
                    ))
                _state.value = state.value.copy(password = event.password, passwordError = null)
            }
            is ConfirmPasswordChanged -> {
                _state.value = state.value.copy(confirmPassword = event.password, confirmPasswordError = null)
            }
            is OnPasswordChanged -> { resetPassword(event.onSuccess, event.onError) }
        }
    }

    private fun validate(): Boolean {
        val passwordMatchValid = state.value.password == state.value.confirmPassword
        val passwordRuleValid = state.value.passwordState?.validate() ?: false
        if (!passwordMatchValid) {
            _state.value = state.value.copy(confirmPasswordError = "Password must be same.")
        } else if (!passwordRuleValid) {
            _state.value = state.value.copy(passwordError = "Password should follow below instruction.")
        }
        return passwordMatchValid && passwordRuleValid
    }

    private fun resetPassword(onSuccess: () -> Unit, onError: (String) -> Unit) = viewModelScope.launch {
        if (!validate()) {
            _state.value = state.value.copy(loading = false)
            return@launch
        }
        _state.value = state.value.copy(loading = true)
        try {
            if (state.value.userId != null && state.value.secret != null) {
                authRepository.resetPassword(
                    state.value.userId!!,
                    state.value.secret!!,
                    state.value.password,
                    state.value.confirmPassword
                )
                onSuccess()
            } else { onError("Invalid link!") }
        } catch (e: Exception) {
            if (e is IOException) {
                _uiEvent.emit(Error(StringResource(R.string.internet_error_msg)))
            } else {
                e.message?.let { onError(it) }
                Timber.e(e)
            }
        }
        _state.value = state.value.copy(loading = false)
    }

}