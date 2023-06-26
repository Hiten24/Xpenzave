package com.hcapps.xpenzave.presentation.change_password

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.domain.usecase.auth.ChangePasswordUseCase
import com.hcapps.xpenzave.presentation.auth.event.PasswordState.Companion.checkAllRule
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.NewPasswordChanged
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.OldPasswordChanged
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.OnPasswordChanged
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
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
): ViewModel() {

    private val _state = mutableStateOf(ChangePasswordSate())
    val state = _state

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is OldPasswordChanged -> {
                _state.value = state.value.copy(
                    oldPassword = event.password,
                    oldPasswordError = null
                )
            }
            is NewPasswordChanged -> {
                _state.value = state.value.copy(newPassword = event.password, newPasswordError = null)
                _state.value = state.value.copy(passwordState = checkAllRule(event.password))
            }
            is OnPasswordChanged -> { changePassword(
                event.onSuccess,
                state.value.oldPassword,
                state.value.newPassword
            ) }
        }
    }

    private fun validate(): Boolean {
        val oldPasswordValid = state.value.oldPassword.length >= 8
        val newPasswordValid = state.value.passwordState?.validate() ?: false
        if (!oldPasswordValid) {
            _state.value = state.value.copy(oldPasswordError = "Invalid password.")
        } else if (!newPasswordValid) {
            _state.value = state.value.copy(newPasswordError = "Password should follow below instruction.")
        }
        return oldPasswordValid && newPasswordValid
    }

    private fun changePassword(onSuccess: () -> Unit, oldPassword: String, newPassword: String) = viewModelScope.launch {
        if (!validate()) {
            _state.value = state.value.copy(loading = false)
            return@launch
        }
        _state.value = state.value.copy(loading = true)
        try {
            changePasswordUseCase(oldPassword, newPassword)
            _state.value = state.value.copy(loading = false)
            onSuccess()
        } catch (e: Exception) {
            if (e is IOException) {
                _uiEvent.emit(Error(StringResource(R.string.internet_error_msg)))
            } else { Timber.e(e) }
            _state.value = state.value.copy(loading = false)
        }
    }

}