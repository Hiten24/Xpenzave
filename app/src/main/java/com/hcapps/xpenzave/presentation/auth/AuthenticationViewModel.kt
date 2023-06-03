package com.hcapps.xpenzave.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var emailState = mutableStateOf("")
        private set

    var passwordState = mutableStateOf("")
        private set

    fun registerUser(onInvalid: () -> Unit) = viewModelScope.launch {
        if (!validateFields()) {
            onInvalid()
            return@launch
        }
        val email = emailState.value
        val password = passwordState.value
        authRepository.createAccountWithCredentials(email, password)
    }

    private fun validateFields(): Boolean {
        return (emailState.value.isEmpty() || passwordState.value.isEmpty()).not()
    }

}