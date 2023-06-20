package com.hcapps.xpenzave.presentation.auth

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

    private fun clearFields() {
        authScreenState.value = authScreenState.value.copy(email = "")
        authScreenState.value = authScreenState.value.copy(password = "")
    }

    private fun authenticationNavArgs() = viewModelScope.launch {
        val oAuth2Segment = savedStateHandle.get<String>(OAUTH2_SEGMENT_ARG_KEY)
        Timber.i("segment: $oAuth2Segment")
        oAuth2Segment?.let { getSessionIfRedirectedFromOauth2(it) }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                authScreenState.value = authScreenState.value.copy(email = event.email)
            }
            is AuthEvent.PasswordChanged -> {
                authScreenState.value = authScreenState.value.copy(password = event.password)
            }
            is AuthEvent.SwitchAuthScreen -> {
                clearFields()
                authScreenState.value = authScreenState.value.copy(authState = event.screen)
            }
        }
    }

    fun registerUser(
        onSuccess: () -> Unit
    ) = viewModelScope.launch {
        if (!validateFields()) {
            onError(Exception("Enter all require details to register."))
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

    fun login(
        onSuccess: () -> Unit
    ) = viewModelScope.launch {
        if (!validateFields()) {
            onError(Exception("Enter all require details to login."))
            return@launch
        }
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

    private fun validateFields(): Boolean {
        return (authScreenState.value.email.isEmpty() || authScreenState.value.password.isEmpty()).not()
    }

    private fun showLoading(loading: Boolean) {
        authScreenState.value = authScreenState.value.copy(loading = loading)
    }

    private fun onError(error: Throwable) = viewModelScope.launch {
        _uiEventFlow.emit(Message(error.message.toString()))
    }

}