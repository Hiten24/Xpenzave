package com.hcapps.xpenzave.presentation.change_password

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.usecase.auth.ChangePasswordUseCase
import com.hcapps.xpenzave.presentation.auth.event.PasswordState.Companion.checkAllRule
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.NewPasswordChanged
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.OldPasswordChanged
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.OnPasswordChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
): ViewModel() {

    private val _state = mutableStateOf(ChangePasswordSate())
    val state = _state

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
        if (!validate()) { return@launch }
        _state.value = state.value.copy(loading = true)
        try {
            changePasswordUseCase(oldPassword, newPassword)
            _state.value = state.value.copy(loading = false)
            onSuccess()
        } catch (e: Exception) {
            Timber.e(e)
        }
        _state.value = state.value.copy(loading = false)
    }

}