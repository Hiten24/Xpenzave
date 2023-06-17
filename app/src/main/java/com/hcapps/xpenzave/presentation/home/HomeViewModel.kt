package com.hcapps.xpenzave.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.local_usecase.LocalExpenseUseCase
import com.hcapps.xpenzave.domain.model.budget.BudgetDomainData
import com.hcapps.xpenzave.domain.usecase.GetBudgetByDateUseCase
import com.hcapps.xpenzave.domain.usecase.GetExpensesUseCase
import com.hcapps.xpenzave.presentation.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBudgetByDateUseCase: GetBudgetByDateUseCase,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val localExpenseUseCase: LocalExpenseUseCase
): ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    init {
        getBudgetOfTheMonth()
        getLocalExpenses(state.value.date)
//        getExpensesOfMonth()
    }

    fun setBudget(budget: Double) {
        _state.value = state.value.copy(budgetAmount = budget)
        _state.value = state.value.copy(budgetPercentage = state.value.calculateBudgetPercentage())
    }

    /*fun deleteExpense(id: String) {
        expenses.removeIf { it.id == id }
        val lastExpense = expenses.groupBy { it.date }.entries.lastOrNull()
        _state.value = state.value.copy(
            recentExpenses = lastExpense?.let { mapOf(it.toPair()) } ?: emptyMap(),
        )
    }*/


    fun onDateChange(date: LocalDate) {
        _state.value = state.value.copy(date = date)
        getBudgetOfTheMonth()
        getLocalExpenses(date)
//        getExpensesOfMonth()
    }

    private fun getBudgetOfTheMonth() = viewModelScope.launch {
        budgetLoading(true)
        try {
            val budget = getBudgetByDateUseCase.execute(state.value.date) ?: BudgetDomainData(date = state.value.date, amount = 0.0)
            _state.value = state.value.copy(budgetAmount = budget.amount, budgetId = budget.id)
            budgetLoading(false)
        } catch (e: Exception) {
            Timber.e(e)
            budgetLoading(false)
        }
    }

    /*private fun getExpensesOfMonth() = viewModelScope.launch {
        expensesLoading(true)
        try {
//            expenses.addAll(getExpensesUseCase.execute(state.value.date, emptyList()))
            // calculating total expenses by adding all expenses amount
            val totalExpense = expenses.sumOf { it.amount }
            // taking last day log of expenses for recent expenses
            val lastExpense = expenses.groupBy { it.date }.entries.lastOrNull()
            _state.value = state.value.copy(
                totalSpending = totalExpense,
                recentExpenses = lastExpense?.let { mapOf(it.toPair()) } ?: emptyMap(),
            )
            _state.value = state.value.copy(budgetPercentage = state.value.calculateBudgetPercentage())
            expensesLoading(false)
        } catch (e: Exception) {
            Timber.e(e)
            expensesLoading(false)
        }
    }*/

    private fun budgetLoading(loading: Boolean) {
        _state.value = state.value.copy(budgetLoading = loading)
    }

    private fun expensesLoading(loading: Boolean) {
        _state.value = state.value.copy(recentExpenseLoading = loading)
    }

    private var getExpenseJob: Job? = null
    private fun getLocalExpenses(date: LocalDate) {
        getExpenseJob?.cancel()
        getExpenseJob = localExpenseUseCase.invoke(date)
            .onEach { iteration ->
                val lastExpense =  iteration.groupBy { it.date }.entries.last()
                _state.value = state.value.copy(
                    recentExpenses = mapOf(lastExpense.toPair())
                )
            }
            .launchIn(viewModelScope)
    }

}