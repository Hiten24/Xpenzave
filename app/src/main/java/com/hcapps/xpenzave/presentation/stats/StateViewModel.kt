package com.hcapps.xpenzave.presentation.stats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private val _state = mutableStateOf(StatsState())
    val state: State<StatsState> = _state

    init {
        getExpenses()
    }

    fun changeScreen(screen: Int) {
        _state.value = state.value.copy(tabScreen = screen)
    }

    fun dateChange(date: LocalDate) {
        _state.value = state.value.copy(date = date)
        getExpenses()
    }

    private fun getExpenses() = viewModelScope.launch {
        loading(true)
        try {
            _state.value = state.value.copy(
                expenses = getCategoriesUseCase.execute(state.value.date)
            )
            loading(false)
        } catch (e: Exception) {
            Timber.e(e)
            loading(false)
        }
    }

    private fun loading(loading: Boolean) {
        _state.value = state.value.copy(loading = loading)
    }

}