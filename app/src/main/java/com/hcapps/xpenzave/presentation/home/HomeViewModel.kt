package com.hcapps.xpenzave.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.model.budget.BudgetDomainData
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import com.hcapps.xpenzave.domain.usecase.GetBudgetByDateUseCase
import com.hcapps.xpenzave.domain.usecase.GetExpensesUseCase
import com.hcapps.xpenzave.presentation.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBudgetByDateUseCase: GetBudgetByDateUseCase,
    private val getExpensesUseCase: GetExpensesUseCase
): ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    val expenses = mutableStateListOf<ExpenseDomainData>()

    /*private fun observeBudget() = viewModelScope.launch {
        savedStateHandle.getStateFlow(UiConstants.BUDGET_VALUE_ARGUMENT_KEY, state.value.budgetAmount).collectLatest {
            _state.value = state.value.copy(budgetAmount = it)
            Timber.i("updated budget: $it")
        }
    }*/

    init {
        getBudgetOfTheMonth()
        getExpensesOfMonth()
//        observeBudget()
    }

    fun setBudget(budget: Double) {
        _state.value = state.value.copy(budgetAmount = budget)
        _state.value = state.value.copy(budgetPercentage = state.value.calculateBudgetPercentage())
    }

    fun deleteExpense(id: String) {
        expenses.removeIf { it.id == id }
        val lastExpense = expenses.groupBy { it.date }.entries.lastOrNull()
        _state.value = state.value.copy(
            recentExpenses = lastExpense?.let { mapOf(it.toPair()) } ?: emptyMap(),
        )
    }


    fun onDateChange(date: LocalDate) {
        _state.value = state.value.copy(date = date)
        getBudgetOfTheMonth()
        getExpensesOfMonth()
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

    private fun getExpensesOfMonth() = viewModelScope.launch {
        expensesLoading(true)
        try {
            expenses.clear()
            expenses.addAll(getExpensesUseCase.execute(state.value.date, emptyList()))
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
    }

    private fun budgetLoading(loading: Boolean) {
        _state.value = state.value.copy(budgetLoading = loading)
    }

    private fun expensesLoading(loading: Boolean) {
        _state.value = state.value.copy(recentExpenseLoading = loading)
    }

}