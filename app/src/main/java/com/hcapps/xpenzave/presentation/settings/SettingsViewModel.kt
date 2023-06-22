package com.hcapps.xpenzave.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.domain.usecase.auth.LogOutUseCase
import com.hcapps.xpenzave.presentation.core.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    dataStore: DataStoreService,
    private val logOutUseCase: LogOutUseCase
): ViewModel() {

    private val _state = mutableStateOf(SettingsState())
    var state = _state

    private val user = dataStore.getUserFlow()

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                email = user.first().email ?: "",
//                currencyCode = user.currencyCode ?: ""
            )
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.LogOut -> {
                logOut(event.onSuccess)
            }
        }
    }

    private fun logOut(onSuccess: () -> Unit) = viewModelScope.launch {
        _state.value = state.value.copy(logOutLoading = true)
        try {
            logOutUseCase()
            onSuccess()
            _state.value = state.value.copy(logOutLoading = false)
        } catch (e: Exception) {
            _state.value = state.value.copy(logOutLoading = false)
            Timber.e(e)
        }
    }

    /*fun setCurrencyPreference(currencyCode: String) = viewModelScope.launch {
        dataStore.saveUser(user.first().copy(currencyCode = currencyCode))
    }*/

}