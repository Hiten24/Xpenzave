package com.hcapps.xpenzave.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.domain.local_usecase.LocalExpenseUseCase
import com.hcapps.xpenzave.domain.usecase.budget.GetBudgetByDateUseCase
import com.hcapps.xpenzave.domain.usecase.expense.GetExpensesUseCase
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UIEvent.Error
import com.hcapps.xpenzave.presentation.core.UiText.StringResource
import com.hcapps.xpenzave.presentation.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBudgetByDateUseCase: GetBudgetByDateUseCase,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val localExpenseUseCase: LocalExpenseUseCase,
    private val localDatabaseRepository: LocalDatabaseRepository
): ViewModel() {

    private var getBudgetJob: Job? = null

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getLocalBudget(state.value.date)
        getLocalExpenses(state.value.date)
        getRemoteData()
    }

    fun onDateChange(date: LocalDate)  {
        _state.value = state.value.copy(date = date)
        getLocalExpenses(date)
        getLocalBudget(date)
        getRemoteData()
    }

    private fun getRemoteData() = viewModelScope.launch {
        try {
            getBudgetByDateUseCase.execute(state.value.date)
            getExpensesUseCase.execute(state.value.date, emptyList())
        } catch (e: IOException) {
            _uiEvent.emit(Error(StringResource(R.string.internet_error_msg)))
        }
    }

    private var getExpenseJob: Job? = null
    private fun getLocalExpenses(date: LocalDate) {
        getExpenseJob?.cancel()
        getExpenseJob = localExpenseUseCase.invoke(date)
            .onEach { iteration ->
                val lastExpense = iteration.groupBy { it.date }.entries.lastOrNull()
                val totalSpend = iteration.sumOf { it.amount }
                _state.value = state.value.copy(
                    recentExpenses = lastExpense?.let { mapOf(it.toPair()) } ?: emptyMap(),
                    totalSpending = totalSpend,
                    budgetPercentage = state.value.calculateBudgetPercentage()
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getLocalBudget(date: LocalDate) {
        Timber.i("budget date: ${date.month.name} ${date.year}")
        getBudgetJob?.cancel()
        getBudgetJob = localDatabaseRepository.getBudget(date)
            .onEach { iterate ->
                _state.value = iterate?.let {
                    state.value.copy(budgetId = it.id, budgetAmount = it.amount, budgetPercentage = state.value.calculateBudgetPercentage())
                } ?: state.value.copy(budgetId = null, budgetAmount = null)
                Timber.i("budget data: $iterate")
            }
            .launchIn(viewModelScope)
    }

}