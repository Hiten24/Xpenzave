package com.hcapps.xpenzave.presentation.auth

import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.User
import com.hcapps.xpenzave.domain.model.toUser
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.AuthScreenState
import com.hcapps.xpenzave.presentation.auth.event.AuthUiEventFlow
import com.hcapps.xpenzave.presentation.auth.event.AuthUiEventFlow.Message
import com.hcapps.xpenzave.util.Constant
import com.hcapps.xpenzave.util.Constant.AUTH_LOGIN_SCREEN
import com.hcapps.xpenzave.util.Constant.AUTH_REGISTER_SCREEN
import com.hcapps.xpenzave.util.UiConstants.OAUTH2_SEGMENT_ARG_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreService
) : ViewModel() {

    var authScreenState = mutableStateOf(AuthScreenState())
        private set

    private val _uiEventFlow = MutableSharedFlow<AuthUiEventFlow>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        authenticationNavArgs()
    }

    private fun authenticationNavArgs() = viewModelScope.launch {
        val oAuth2Segment = savedStateHandle.get<String>(OAUTH2_SEGMENT_ARG_KEY)
        Timber.i("segment: $oAuth2Segment")
        oAuth2Segment?.let { getSessionIfRedirectedFromOauth2(it) }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                authScreenState.value = authScreenState.value.copy(email = event.email, emailError = null)
            }
            is AuthEvent.PasswordChanged -> {
                authScreenState.value = authScreenState.value.copy(password = event.password, passwordError = null)
            }
            is AuthEvent.ConfirmPasswordChanged -> {
//                authScreenState.value = authScreenState.value.copy(confirmPassword = event.password)
            }
            is AuthEvent.SwitchAuthScreen -> {
                authScreenState.value = AuthScreenState(authState = event.screen)
            }
            else ->{}
        }
    }

    fun registerUser(
        onSuccess: () -> Unit
    ) = viewModelScope.launch {
        if (!validate()) {
            return@launch
        }
        showLoading(true)
        val email = authScreenState.value.email
        val password = authScreenState.value.password
        when (val response = authRepository.createAccountWithCredentials(email, password)) {
            is RequestState.Success -> {
                val user = response.data
                dataStore.saveUser(User(userId = user.id, email = user.email, currencyCode = null))
                showLoading(false)
                onSuccess()
            }
            is RequestState.Error -> {
                showLoading(false)
                onError(response.error)
            }
            else -> Unit
        }
    }

    fun login(onSuccess: () -> Unit) = viewModelScope.launch {
        if (!validate()) { return@launch }

        showLoading(true)
        val email = authScreenState.value.email
        val password = authScreenState.value.password
        when (val response = authRepository.loginWithCredentials(email, password)) {
            is RequestState.Success -> {
                val user = response.data
                dataStore.saveUser(User(userId = user.userId, email = email, currencyCode = null))
                showLoading(false)
                onSuccess()
            }
            is RequestState.Error -> {
                if (response.error.message == "Invalid credentials. Please check the email and password.") {
                    authScreenState.value = authScreenState.value.copy(passwordError = "Invalid credentials.")
                }
                showLoading(false)
                onError(response.error)
            }
            else -> Unit
        }
    }

    fun loginWithOath2(
        activity: ComponentActivity,
        onSuccess: () -> Unit,
        provider: String
    ) = viewModelScope.launch {
        when (val response = authRepository.authenticateWithOauth2(activity, provider)) {
            is RequestState.Success -> onSuccess()
            is RequestState.Error -> onError(response.error)
            else -> Unit
        }
    }

    private fun getAccount(onSuccess: () -> Unit) = viewModelScope.launch {
        showLoading(true)
        when (val response = authRepository.getAccount()) {
            is RequestState.Success -> {
                val user = response.data
                dataStore.saveUser(user.toUser())
                Timber.i("user: $user")
                showLoading(false)
                onSuccess()
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                _uiEventFlow.emit(Message(response.error.message.toString()))
                showLoading(false)
            }
            else -> {}
        }
    }

    private fun getSessionIfRedirectedFromOauth2(segment: String) {
        when (segment) {
            Constant.OAUTH2_SUCCESS_SUFFIX -> {
                getAccount {
                    viewModelScope.launch { _uiEventFlow.emit(AuthUiEventFlow.OAuth2Success) }
                }
                Timber.i("success")
            }
            Constant.OAUTH2_FAILED_SUFFIX -> {
                viewModelScope.launch { _uiEventFlow.emit(Message("Authentication failed.")) }
                Timber.i("failed")
            }
            else -> {
                Timber.i("else")
            }
        }
    }

    private fun validate(): Boolean {
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(authScreenState.value.email).matches()
        when {
            emailValid.not() -> {
                authScreenState.value = authScreenState.value.copy(emailError = "Enter valid email address.")
            }
            authScreenState.value.password.length < 8 && authScreenState.value.authState == AUTH_LOGIN_SCREEN -> {
                authScreenState.value = authScreenState.value.copy(passwordError = "Invalid password.")
            }
            /*authScreenState.value.password.length < 8 && authScreenState.value.confirmPassword.length < 8 && authScreenState.value.authState == AUTH_REGISTER_SCREEN -> {
                authScreenState.value = authScreenState.value.copy(passwordError = "Password Must be at least 8 chars long.")
            }*/
            else -> {}
        }
        return authScreenState.value.email.isNotEmpty()
                && authScreenState.value.password.isNotEmpty()
                && emailValid
                && authScreenState.value.password.length >= 8
    }

    private fun showLoading(loading: Boolean) {
        authScreenState.value = authScreenState.value.copy(loading = loading)
    }

    private fun onError(error: Throwable) = viewModelScope.launch {
        _uiEventFlow.emit(Message(error.message.toString()))
    }

}