package com.hcapps.xpenzave.presentation.stats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import com.hcapps.xpenzave.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private val _state = mutableStateOf(StatsState())
    val state: State<StatsState> = _state

    init {
        getExpenses()
//        dummyExpenses()
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

    private fun dummyExpenses(date: LocalDate = LocalDate.now()) = viewModelScope.launch {
        loading(true)
        delay(5000L)
        _state.value = state.value.copy( expenses = getDummyExpenseByDate(date))
        loading(false)
    }

}

private fun getDummyExpenseByDate(date: LocalDate): Map<LocalDate, List<ExpenseDomainData>> {
    return if (date.monthValue == 5 && date.year == 2023) {
        mayDummyExpense
    } else if (date.monthValue == 6 && date.year == 2023) {
        juneDummyExpense
    } else if (date.monthValue == 4 && date.year == 2022) {
        lastYearExpense
    } else {
        emptyMap()
    }
}

private val lastYearExpense  = mapOf(
    Pair(
        LocalDate.now(),
        listOf(
            ExpenseDomainData.dummy(LocalDate.of(2022, 4, 30)),
            ExpenseDomainData.dummy(LocalDate.of(2022, 4, 27)),
            ExpenseDomainData.dummy(LocalDate.of(2022, 4, 25)),
        )
    )
)

private val juneDummyExpense = mapOf(
    Pair(LocalDate.now(), listOf(ExpenseDomainData.dummy()))
)

private val mayDummyExpense = mapOf(
    Pair(
        LocalDate.now(),
        listOf(
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 1)),
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 1)),
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 1)),
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 1)),
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 2)),
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 3)),
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 4)),
            ExpenseDomainData.dummy(LocalDate.of(2023, 5, 4)),
        )
    )
)