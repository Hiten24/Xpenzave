package com.hcapps.xpenzave.presentation.add_expense

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
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
import com.hcapps.xpenzave.presentation.core.UIEvent.Error
import com.hcapps.xpenzave.presentation.core.UIEvent.ShowMessage
import com.hcapps.xpenzave.presentation.core.UiText.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val addExpenseUseCase: AddExpenseUseCase
): ViewModel() {

    private val _state = mutableStateOf(AddExpenseState())
    val state: State<AddExpenseState> = _state

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            is AmountChange -> {
                _state.value = state.value.copy(amount = event.amount)
            }
            is CategoryChange -> {
                _state.value = state.value.copy(category = event.category)
            }
            is DetailsChange -> {
                _state.value = state.value.copy(details = event.detail)
            }
            is AddBillEachMonthChange -> {
                _state.value = state.value.copy(
                    eachMonth = !state.value.eachMonth
                )
            }
            is PhotoChange -> {
                _state.value = state.value.copy(photo = event.photo)
            }
            is AddExpenseEvent.ClearPhoto -> {
                _state.value = state.value.copy(photo = null)
            }
            is AddExpenseEvent.DateTimeChange -> {
                _state.value = state.value.copy(date = event.dateTime)
            }
            is AddExpenseEvent.AddButtonClicked -> {
                uploadPhotoAndAddExpense(state.value.photo)
            }
        }
    }

    private fun loading(loading: Boolean) {
        _state.value = state.value.copy(loading = loading)
    }

    private fun clearState() {
        _state.value = AddExpenseState()
    }

    private fun validate(): Boolean {
        val amount = state.value.amount.toDoubleOrNull()
        val category = state.value.category
        when {
            amount == null || amount == 0.0 -> {
                _state.value = state.value.copy(amountError = "Amount can't be empty.")
            }
            category.isNullOrEmpty() -> {
                viewModelScope.launch {
                    _uiEvent.emit(ShowMessage("Select category to add expense."))
                }
            }
        }
        return (amount != null && amount != 0.0 && category.isNullOrEmpty().not())
    }

    private fun uploadPhotoAndAddExpense(uri: Uri?) = viewModelScope.launch {
        loading(true)
        if (validate().not()) {
            loading(false)
            return@launch
        }
        try {
            val uploadedPhoto = uri?.let { uploadPhotoUseCase(it) }
            _state.value = state.value.copy(uploadedPhoto = uploadedPhoto)
            addExpenseUseCase(getTypedExpense())
            loading(false)
            clearState()
            _uiEvent.emit(ShowMessage("Expense Added Successfully!"))
        } catch (e: Exception) {
            if (e is IOException) {
                _uiEvent.emit(Error(StringResource(R.string.internet_error_msg)))
            }
            Timber.e(e)
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