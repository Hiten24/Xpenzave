package com.hcapps.xpenzave.presentation.edit_budget

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.domain.model.budget.BudgetData
import com.hcapps.xpenzave.domain.usecase.budget.EditBudgetUseCase
import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditBudgetViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val editBudgetUseCase: EditBudgetUseCase
): ViewModel() {

    private val _state = mutableStateOf(BudgetState())
    val state: State<BudgetState> = _state

    private val _uiFlow = MutableSharedFlow<BudgetScreenFlow>()
    val uiFlow = _uiFlow.asSharedFlow()

    init {
        getMonthYearArgument()
    }

    private fun clearState() {
        _state.value = BudgetState()
    }

    private fun getMonthYearArgument() {
        val date = savedStateHandle.get<String>(key = EDIT_BUDGET_ARGUMENT_KEY)
        val budgetId = savedStateHandle.get<String>(key = EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY)
        _state.value = state.value.copy(budgetId = if (budgetId.isNullOrEmpty()) null else budgetId)
        _state.value = state.value.copy(date = LocalDate.parse(date))
    }

    fun onAmountChange(amount: String) {
        _state.value = state.value.copy(amount = amount, amountError = null)
    }

    private fun validate(): Boolean {
        val amt = state.value.amount.toDoubleOrNull() ?: 0.0
        return if (amt == 0.0) {
            _state.value = state.value.copy(amountError = "Budget can't be zero or empty")
            false
        } else true
    }

    fun upsertBudget() = viewModelScope.launch {
        loading(true)
        if (!validate()) {
            loading(false)
            return@launch
        }
        val date = state.value.date ?: LocalDate.now()
        val budget = BudgetData(date.monthValue, date.year, state.value.amount.toDoubleOrNull() ?: 0.0)
        try {
            editBudgetUseCase(state.value.budgetId, budget)
            loading(false)
            clearState()
            _uiFlow.emit(BudgetScreenFlow.SnackBar("Budget updated successfully."))
            delay(1000L)
            _uiFlow.emit(BudgetScreenFlow.NavigateUp)
        } catch (e: Exception) {
            e.printStackTrace()
            loading(false)
        }
    }

    private fun loading(loading: Boolean) {
        _state.value = state.value.copy(loading = loading)
    }

}