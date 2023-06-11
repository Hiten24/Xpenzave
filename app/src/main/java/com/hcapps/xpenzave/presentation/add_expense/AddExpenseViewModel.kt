package com.hcapps.xpenzave.presentation.add_expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.APP_WRITE_DATE_TIME_FORMAT
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.ChangeAddBillEachMonth
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.EnterAmount
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.EnterDetails
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.SelectCategory
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseState
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UIEvent.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    val state = mutableStateOf(AddExpenseState())

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            is EnterAmount -> {
                state.value = state.value.copy(amount = event.amount)
            }
            is SelectCategory -> {
               state.value = state.value.copy(category = event.category)
            }
            is EnterDetails -> {
                state.value = state.value.copy(details = event.detail)
            }
            is ChangeAddBillEachMonth -> {
                state.value = state.value.copy(
                    eachMonth = !state.value.eachMonth
                )
            }
        }
    }

    private fun clearState() {
        state.value = AddExpenseState()
    }

    private fun validate(): Boolean {
        val amount = state.value.amount.toDoubleOrNull()
        return (amount != null && amount != 0.0)
    }

    fun addExpense() = viewModelScope.launch {
        if (validate().not()) {
            state.value = state.value.copy(amountError = "Amount can't be empty.")
            return@launch
        }
        _uiEventFlow.emit(Loading(true))
        val expense = ExpenseData(
            amount = state.value.amount.toDoubleOrNull() ?: 0.0,
            details = state.value.details,
            categoryId = state.value.category,
            date = state.value.date.format(DateTimeFormatter.ofPattern(APP_WRITE_DATE_TIME_FORMAT)),
            day = state.value.date.dayOfMonth,
            month = state.value.date.monthValue,
            year = state.value.date.year,
            addThisExpenseToEachMonth = state.value.eachMonth
        )
        when (val response = databaseRepository.addExpense(expense)) {
            is RequestState.Success -> {
                _uiEventFlow.emit(Loading(false))
                clearState()
            }
            is RequestState.Error -> {
                _uiEventFlow.emit(Loading(false))
            }
            else -> Unit
        }

    }

    fun getCategories() = viewModelScope.launch {
        when (val response = databaseRepository.getCategories()) {
            is RequestState.Success -> {
                Timber.i("----------------------------- Category -----------------------------")
                response.data.forEach { category ->
                    Timber.i("${category.id} - ${category.data}")
                }
                Timber.i("--------------------------------------------------------------------")
            }
            is RequestState.Error -> {
                Timber.e(response.error)
            }
            else -> Unit
        }
    }

}