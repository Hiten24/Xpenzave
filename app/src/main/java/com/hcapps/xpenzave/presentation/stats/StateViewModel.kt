package com.hcapps.xpenzave.presentation.stats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import com.hcapps.xpenzave.domain.usecase.GetExpensesUseCase
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_FILTER_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.extensions.fromJson
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getExpensesUseCase: GetExpensesUseCase
): ViewModel() {

    private val _state = mutableStateOf(StatsState())
    val state: State<StatsState> = _state

    var expenses = mutableStateListOf<ExpenseDomainData>()
    var appliedFilter: List<String> = emptyList()

    init {
        getFilterArgs()
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
        appliedFilter = emptyList()
    }

    private fun getExpenses(filter: List<String>) = viewModelScope.launch {
        loading(true)
        try {
            expenses = getExpensesUseCase.execute(state.value.date, filter).toMutableStateList()
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