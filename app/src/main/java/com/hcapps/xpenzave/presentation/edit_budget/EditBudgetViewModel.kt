package com.hcapps.xpenzave.presentation.edit_budget

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.budget.BudgetData
import com.hcapps.xpenzave.presentation.core.component.button.ButtonState
import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditBudgetViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private val _state = mutableStateOf(BudgetState())
    val state: State<BudgetState> = _state

    init {
        getMonthYearArgument()
    }

    private fun getMonthYearArgument() {
        val monthYear = savedStateHandle.get<String>(key = EDIT_BUDGET_ARGUMENT_KEY)?.split("-")
        monthYear?.let { _state.value = state.value.copy(date = LocalDate.of(it[1].toInt(), it[0].toInt(), 1)) }
    }

    fun onAmountChange(amount: String) {
        _state.value = state.value.copy(amount = amount)
    }

    private fun validate(): Boolean {
        val amt = state.value.amount.toDoubleOrNull() ?: 0.0
        return if (amt == 0.0) {
            _state.value = state.value.copy(amountError = "Budget can't be zero or empty")
            false
        } else true
    }

    fun updateBudget() = viewModelScope.launch {
        loading(true)
        if (!validate()) return@launch
        val date = state.value.date ?: LocalDate.now()
        val budget = BudgetData(date.monthValue, date.year, state.value.amount.toDouble())
        when (val response = databaseRepository.createBudget(budget)) {
            is RequestState.Success -> {
                loading(false)
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                loading(false)
            }
            else -> {}
        }
    }

    private fun loading(loading: Boolean) {
        _state.value = state.value.copy(buttonState = ButtonState(loading = loading))
    }

}