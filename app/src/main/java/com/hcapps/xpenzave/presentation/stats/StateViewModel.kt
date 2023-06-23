package com.hcapps.xpenzave.presentation.stats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.local_usecase.LocalExpenseUseCase
import com.hcapps.xpenzave.domain.usecase.GetExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val localExpenseUseCase: LocalExpenseUseCase,
): ViewModel() {

    private val _state = mutableStateOf(StatsState())
    val state: State<StatsState> = _state

    var appliedFilter: List<String> = emptyList()

//    private val _generalState = mutableStateOf(StatisticsState())
//    val generalState: State<StatisticsState> = _generalState

    private var getExpenseJob: Job? = null

    init {
        getExpenses(emptyList())
    }

    fun applyFilters(selectedCategories: List<String>) {
        appliedFilter = selectedCategories
        getLocalExpenses(state.value.date, selectedCategories)
    }

    /*fun changeScreen(screen: Int) {
        _state.value = state.value.copy(tabScreen = screen)
    }*/

    fun dateChange(date: LocalDate) {
        _state.value = state.value.copy(date = date)
        getLocalExpenses(date)
        getExpenses(emptyList())
        appliedFilter = emptyList()
    }

    private fun getExpenses(filter: List<String>) = viewModelScope.launch {
         loading(true)
        try {
            getExpensesUseCase.execute(state.value.date, filter)
            loading(false)
        } catch (e: Exception) {
            Timber.e(e)
            loading(false)
        }
    }

    private fun loading(loading: Boolean) {
        _state.value = state.value.copy(loading = loading)
    }

    private fun getLocalExpenses(date: LocalDate, filter: List<String> = emptyList()) {
        getExpenseJob?.cancel()
        getExpenseJob = localExpenseUseCase.invoke(date, filter)
            .onEach {
                _state.value = state.value.copy(
                    expenses = it
                )
            }
            .launchIn(viewModelScope)
    }
}