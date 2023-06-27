package com.hcapps.xpenzave.presentation.auth.forgot_password

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.domain.usecase.auth.ForgotPasswordUseCase
import com.hcapps.xpenzave.presentation.auth.forgot_password.ForgotPasswordEvent.ContinueButton
import com.hcapps.xpenzave.presentation.auth.forgot_password.ForgotPasswordEvent.EmailChange
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
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
): ViewModel() {

    private val _state = mutableStateOf(ForgotPasswordState())
    val state: State<ForgotPasswordState> = _state

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is EmailChange -> {
                _state.value = state.value.copy(email = event.email, emailError = null)
            }
            is ContinueButton -> {
                if (state.value.email.isNotEmpty() && state.value.emailError == null && !state.value.loading) {
                    forgotPassword(event.onSuccess)
                }
            }
        }
    }

    private fun forgotPassword(onSuccess: () -> Unit) = viewModelScope.launch {
        _state.value = state.value.copy(loading = true)
        if (Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches().not()) {
            _state.value = state.value.copy(emailError = "Enter valid email address.", loading = false)
            return@launch
        }
        try {
            forgotPasswordUseCase(state.value.email)
            onSuccess()
        }
        catch (e: Exception) {
            if (e is IOException) {
                _uiEvent.emit(Error(StringResource(R.string.internet_error_msg)))
            } else if (e.message == "User with the requested ID could not be found.") {
                _state.value = state.value.copy(emailError = "Use with the email Id not found.")
            } else {
                Timber.e(e)
            }
            _state.value = state.value.copy(loading = false)
        }
    }

}