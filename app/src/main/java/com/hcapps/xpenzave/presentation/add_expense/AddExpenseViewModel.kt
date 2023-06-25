package com.hcapps.xpenzave.presentation.add_expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.remote_source.APP_WRITE_DATE_TIME_FORMAT
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.domain.usecase.expense.AddExpenseUseCase
import com.hcapps.xpenzave.domain.usecase.storage.UploadPhotoUseCase
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.AddBillEachMonthChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.AmountChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.CategoryChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.DetailsChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.PhotoChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseState
import com.hcapps.xpenzave.presentation.core.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val addExpenseUseCase: AddExpenseUseCase
): ViewModel() {

    val state = mutableStateOf(AddExpenseState())

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            is AmountChange -> {
                state.value = state.value.copy(amount = event.amount)
            }
            is CategoryChange -> {
               state.value = state.value.copy(category = event.category)
            }
            is DetailsChange -> {
                state.value = state.value.copy(details = event.detail)
            }
            is AddBillEachMonthChange -> {
                state.value = state.value.copy(
                    eachMonth = !state.value.eachMonth
                )
            }
            is PhotoChange -> {
                state.value = state.value.copy(
                    photo = event.photo,
                    photoPath = event.Path
                )
            }
            is AddExpenseEvent.ClearPhoto -> {
                state.value = state.value.copy(photo = null)
            }
            is AddExpenseEvent.DateTimeChange -> {
                state.value = state.value.copy(date = event.dateTime)
            }
            is AddExpenseEvent.AddButtonClicked -> {
                uploadPhotoAndAddExpense(state.value.photoPath)
            }
        }
    }

    private fun loading(loading: Boolean) {
        state.value = state.value.copy(loading = loading)
    }

    private fun clearState() {
        state.value = AddExpenseState()
    }

    private fun validate(): Boolean {
        val amount = state.value.amount.toDoubleOrNull()
        val category = state.value.category
        when {
            amount == null || amount == 0.0 -> {
                state.value = state.value.copy(amountError = "Amount can't be empty.")
            }
            category.isNullOrEmpty() -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(UIEvent.ShowMessage("Select category to add expense."))
                }
            }
        }
        return (amount != null && amount != 0.0 && category.isNullOrEmpty().not())
    }

    private fun uploadPhotoAndAddExpense(path: String?) = viewModelScope.launch {
        loading(true)
        if (validate().not()) {
            loading(false)
            return@launch
        }
        try {
            path?.let { uploadPhotoUseCase(it) }
            addExpenseUseCase(getTypedExpense())
            loading(false)
            clearState()
        } catch (e: Exception) {
            e.printStackTrace()
            loading(false)
        }
    }

    private fun getTypedExpense(): ExpenseData {
        return ExpenseData(
            amount = state.value.amount.toDoubleOrNull() ?: 0.0,
            details = state.value.details,
            categoryId = state.value.category ?: "",
            date = state.value.date.format(DateTimeFormatter.ofPattern(APP_WRITE_DATE_TIME_FORMAT)),
            day = state.value.date.dayOfMonth,
            month = state.value.date.monthValue,
            year = state.value.date.year,
            addThisExpenseToEachMonth = state.value.eachMonth,
            photo = state.value.uploadedPhoto?.fileId
        )
    }

}