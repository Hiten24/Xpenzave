package com.hcapps.xpenzave.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.User
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreService
): ViewModel() {

    var state = mutableStateOf(SettingsState())
        private set

    private val user = dataStore.getUserFlow()

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            state.value = state.value.copy(
                email = user.first().email ?: "",
//                currencyCode = user.currencyCode ?: ""
            )
        }
    }

    fun logOut(onSuccess: () -> Unit) = viewModelScope.launch {
        when (val response = authRepository.logOut()) {
            is ResponseState.Success -> {
                dataStore.saveUser(User())
                onSuccess()
            }
            is ResponseState.Error -> { onError(response.error) }
            else -> Unit
        }
    }

    fun setCurrencyPreference(currencyCode: String) = viewModelScope.launch {
        dataStore.saveUser(user.first().copy(currencyCode = currencyCode))
    }

    private fun onError(error: Throwable) = viewModelScope.launch {
        _uiEventFlow.emit(UIEvent.Error(error))
    }

}