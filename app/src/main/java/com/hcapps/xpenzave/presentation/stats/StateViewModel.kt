package com.hcapps.xpenzave.presentation.stats

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.model.budget.BudgetDomainData
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import com.hcapps.xpenzave.domain.usecase.GetBudgetByDateUseCase
import com.hcapps.xpenzave.domain.usecase.GetExpensesUseCase
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryBarChartData
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_FILTER_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.extensions.fromJson
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getBudgetByDateUseCase: GetBudgetByDateUseCase,
): ViewModel() {

    private val _state = mutableStateOf(StatsState())
    val state: State<StatsState> = _state

    var expenses = mutableStateListOf<ExpenseDomainData>()
    var appliedFilter: List<String> = emptyList()

    private val _generalState = mutableStateOf(StatisticsState())
    val generalState: State<StatisticsState> = _generalState

    init {
        getFilterArgs()
        getBudgetOfTheMonth()
    }

    fun deleteExpense(id: String) {
        expenses.removeIf { it.id == id }
    }

    private fun getFilterArgs() {
        val filters = savedStateHandle.get<String>(key = EXPENSE_FILTER_ARGUMENT_KEY)?.fromJson<List<String>>()
        appliedFilter = filters ?: emptyList()
        getExpenses(appliedFilter)
    }

    fun changeScreen(screen: Int) {
        _state.value = state.value.copy(tabScreen = screen)
    }

    fun dateChange(date: LocalDate) {
        _state.value = state.value.copy(date = date)
        getExpenses(emptyList())
        getBudgetOfTheMonth()
        appliedFilter = emptyList()
    }

    private fun getExpenses(filter: List<String>) = viewModelScope.launch {
        loading(true)
        try {
            expenses = getExpensesUseCase.execute(state.value.date, filter).toMutableStateList()
            calculateGeneralState()
            loading(false)
        } catch (e: Exception) {
            Timber.e(e)
            loading(false)
        }
    }

    private fun getBudgetOfTheMonth() = viewModelScope.launch {
        try {
            val budget = getBudgetByDateUseCase.execute(state.value.date) ?: BudgetDomainData(date = state.value.date, amount = 0.0)
            _generalState.value = generalState.value.copy(budget = budget.amount)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun calculateGeneralState() = viewModelScope.launch {
        try {
            val totalSpending = expenses.sumOf { it.amount }
            val budget = generalState.value.budget
            val totalPercentage = ((totalSpending / budget) * 100).roundToInt()
            _generalState.value = generalState.value.copy(
                totalSpend = totalSpending,
                budgetPercentage = totalPercentage,
                categoryBarChartData = calculateBarGraphData(budget, totalSpending, totalPercentage)
            )
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun calculateBarGraphData(budget: Double, totalSpending: Double, totalPercentage: Int): List<CategoryBarChartData> {
        val categoriesBarGraphData = mutableListOf(
            CategoryBarChartData(
                Category("", "Total", Icons.Outlined.Summarize, true),
                budget = budget,
                totalSpendByCategory = totalSpending,
                budgetPercentageByCategory = totalPercentage
            )
        )
        try {
            Category.dummies().forEach { category ->
                val categorySpend =
                    expenses.filter { it.category == category.id }.sumOf { it.amount }
                categoriesBarGraphData.add(
                    CategoryBarChartData(
                        category = category,
                        budget = budget,
                        totalSpendByCategory = categorySpend,
                        budgetPercentageByCategory = ((categorySpend / budget) * 100).roundToInt()
                    )
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return categoriesBarGraphData
    }

    private fun loading(loading: Boolean) {
        _state.value = state.value.copy(loading = loading)
    }

}