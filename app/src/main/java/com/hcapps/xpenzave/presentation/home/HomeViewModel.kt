package com.hcapps.xpenzave.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.usecase.GetBudgetByDateUseCase
import com.hcapps.xpenzave.domain.usecase.GetCategoriesUseCase
import com.hcapps.xpenzave.presentation.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBudgetByDateUseCase: GetBudgetByDateUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    init {
        getBudgetOfTheMonth()
        getExpensesOfMonth()
    }

    fun onDateChange(date: LocalDate) {
        _state.value = state.value.copy(date = date)
    }

    private fun getBudgetOfTheMonth() = viewModelScope.launch {
        budgetLoading(true)
        try {
            val budget = getBudgetByDateUseCase.execute(state.value.date) ?: return@launch Timber.e("Something went wrong")
            _state.value = state.value.copy(budgetAmount = budget.amount)
            budgetLoading(false)
        } catch (e: Exception) {
            Timber.e(e)
            budgetLoading(false)
        }
    }

    private fun getExpensesOfMonth() = viewModelScope.launch {
        expensesLoading(true)
        try {
            val expenses = getCategoriesUseCase.execute(state.value.date)
            // calculating total expenses by adding all expenses amount
            val totalExpense = expenses.map { it.value.sumOf { it.amount } }.sumOf { it }
            // taking last day log of expenses for recent expenses
            val lastExpense = expenses.entries.lastOrNull()
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