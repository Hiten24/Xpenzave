package com.hcapps.xpenzave.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.auth.AuthRepository
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.util.ResponseState
import com.hcapps.xpenzave.util.SettingsDataStore
import com.hcapps.xpenzave.util.SettingsDataStore.Companion.CURRENCY_CODE_KEY
import com.hcapps.xpenzave.util.SettingsDataStore.Companion.SETTINGS_IS_LOGGED_IN_KEY
import com.hcapps.xpenzave.util.SettingsDataStore.Companion.USER_EMAIL_KEY
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
            state.value = state.value.copy(email = settingsDataStore.getString(USER_EMAIL_KEY))
            state.value = state.value.copy(currencyCode = settingsDataStore.getString(CURRENCY_CODE_KEY))
        }
    }

    fun logOut(onSuccess: () -> Unit) = viewModelScope.launch {
        when (val response = authRepository.logOut()) {
            is ResponseState.Success -> {
                settingsDataStore.saveBoolean(SETTINGS_IS_LOGGED_IN_KEY, false)
                onSuccess()
            }
            is ResponseState.Error -> { onError(response.error) }
            else -> Unit
        }
    }

    fun setCurrencyPreference(currencyCode: String) = viewModelScope.launch {
        settingsDataStore.saveString(CURRENCY_CODE_KEY, currencyCode)
    }

    private fun onError(error: Throwable) = viewModelScope.launch {
        _uiEventFlow.emit(UIEvent.Error(error))
    }

}