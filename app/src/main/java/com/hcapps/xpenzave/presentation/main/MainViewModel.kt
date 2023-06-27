package com.hcapps.xpenzave.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.toUser
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UIEvent.Error
import com.hcapps.xpenzave.presentation.core.UiText.StringResource
import com.hcapps.xpenzave.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreService
): ViewModel() {

    private val _sessionState = mutableStateOf(SessionState())
    val sessionState: State<SessionState> = _sessionState

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

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
                    if (response.error is IOException) {
                        if (dataStore.user.first().userId.isNotEmpty()) {
                            _sessionState.value = sessionState.value.copy(startDestination = Screen.Home.route, loading = false)
                        } else {
                            _uiEvent.emit(Error(StringResource(R.string.internet_error_msg)))
                        }
                    } else {
                        Timber.e(response.error)
                    }
                    _sessionState.value = sessionState.value.copy(loading = false)
                }
                else -> _sessionState.value = sessionState.value.copy(loading = false)
            }
        }
    }

}