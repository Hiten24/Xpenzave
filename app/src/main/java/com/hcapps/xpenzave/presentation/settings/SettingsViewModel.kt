package com.hcapps.xpenzave.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.domain.usecase.auth.LogOutUseCase
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UIEvent.Error
import com.hcapps.xpenzave.presentation.core.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
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
            if (e is IOException) {
                _uiEventFlow.emit(Error(UiText.StringResource(R.string.internet_error_msg)))
            } else {
                Timber.e(e)
            }
            _state.value = state.value.copy(logOutLoading = false)
        }
    }

    /*fun setCurrencyPreference(currencyCode: String) = viewModelScope.launch {
        dataStore.saveUser(user.first().copy(currencyCode = currencyCode))
    }*/

}