package com.hcapps.xpenzave.presentation.add_expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.APP_WRITE_DATE_TIME_FORMAT
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.data.source.remote.repository.storage.StorageRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.AddBillEachMonthChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.AmountChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.CategoryChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.DetailsChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.PhotoChange
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseState
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.component.button.ButtonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val storageRepository: StorageRepository
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
                state.value = state.value.copy(photo = event.photo)
                event.photo?.let { uploadPhoto(event.Path) }
            }
            is AddExpenseEvent.ClearPhoto -> {
                state.value = state.value.copy(photo = null)
                state.value.uploadedPhoto?.fileId?.let { deletePhoto(it) }
            }
        }
    }

    private fun loading(loading: Boolean) {
        state.value = state.value.copy(
            addButtonState = ButtonState(loading = loading)
        )
    }

    private fun enabledButton(enable: Boolean) {
        state.value = state.value.copy(addButtonState = ButtonState(enabled = enable))
    }

    private fun clearState() {
        state.value = AddExpenseState()
    }

    private fun validate(): Boolean {
        val amount = state.value.amount.toDoubleOrNull()
        return (amount != null && amount != 0.0)
    }

    private fun uploadPhoto(path: String) = viewModelScope.launch {
        enabledButton(false)
        state.value = state.value.copy(uploadPhotoProgress = true)
        when (val response = storageRepository.createFile(path)) {
            is RequestState.Success -> {
                Timber.i("name: ${response.data.name}, id: ${response.data.fileId}")
                state.value = state.value.copy(uploadedPhoto = response.data)
                enabledButton(true)
                state.value = state.value.copy(uploadPhotoProgress = false)
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                enabledButton(true)
                state.value = state.value.copy(uploadPhotoProgress = false)
            }
            else -> {}
        }
    }

    private fun deletePhoto(fileId: String) = viewModelScope.launch {
        loading(true)
        when (val response = storageRepository.deleteFile(fileId)) {
            is RequestState.Success -> {
                state.value = state.value.copy(uploadedPhoto = null)
                loading(false)
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                loading(false)
            }
            else -> {}
        }
    }

    fun addExpense() = viewModelScope.launch {
        if (validate().not()) {
            state.value = state.value.copy(amountError = "Amount can't be empty.")
            return@launch
        }
        loading(true)
        val expense = ExpenseData(
            amount = state.value.amount.toDoubleOrNull() ?: 0.0,
            details = state.value.details,
            categoryId = state.value.category,
            date = state.value.date.format(DateTimeFormatter.ofPattern(APP_WRITE_DATE_TIME_FORMAT)),
            day = state.value.date.dayOfMonth,
            month = state.value.date.monthValue,
            year = state.value.date.year,
            addThisExpenseToEachMonth = state.value.eachMonth,
            photo = state.value.uploadedPhoto?.fileId
        )
        when (val response = databaseRepository.addExpense(expense)) {
            is RequestState.Success -> {
                loading(false)
                clearState()
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                loading(false)
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