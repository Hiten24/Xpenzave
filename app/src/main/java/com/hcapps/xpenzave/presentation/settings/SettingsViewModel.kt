package com.hcapps.xpenzave.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.AuthRepository
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.util.ResponseState
import com.hcapps.xpenzave.util.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val authRepository: AuthRepository
): ViewModel() {

    var state = mutableStateOf(SettingsState())
        private set

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            state.value = state.value.copy(email = settingsDataStore.getString(SettingsDataStore.USER_EMAIL_KEY))
        }
    }

    fun logOut(onSuccess: () -> Unit) = viewModelScope.launch {
        when (val response = authRepository.logOut()) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SettingsDataStore.SETTINGS_IS_LOGGED_IN_KEY, false)
                onSuccess()
            }
            is ResponseState.Error -> { onError(response.error) }
            else -> Unit
        }
    }

    private fun onError(error: Throwable) = viewModelScope.launch {
        _uiEventFlow.emit(UIEvent.Error(error))
    }

}