package com.hcapps.xpenzave.presentation.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.toUser
import com.hcapps.xpenzave.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _sessionState = mutableStateOf(SessionState())
    val sessionState = _sessionState

    init {
        getActiveSession()
    }

    private fun getActiveSession() {
        viewModelScope.launch {
             when (val response = authRepository.getAccount()) {
                is RequestState.Success -> {
                    val user = response.data.toUser()
                    if (user.userId.isNotEmpty()) {
                        _sessionState.value = sessionState.value.copy(startDestination = Screen.Home.route, loading = false)
                    } else {
                        _sessionState.value = sessionState.value.copy(startDestination = Screen.OnBoard.route, loading = false)
                    }
                }
                is RequestState.Error -> {
                    Timber.e(response.error)
                    _sessionState.value = sessionState.value.copy(loading = false)
                }
                else -> _sessionState.value = sessionState.value.copy(loading = false)
            }
        }
    }

}