package com.hcapps.xpenzave.presentation.expense_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.data.remote_source.repository.storage.StorageRepository
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.domain.usecase.expense.DeleteExpenseUseCase
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.UIEvent.Error
import com.hcapps.xpenzave.presentation.core.UiText.StringResource
import com.hcapps.xpenzave.presentation.edit_budget.BudgetScreenFlow
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_DETAIL_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.extensions.fromJson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val storageRepository: StorageRepository,
): ViewModel() {

    private val _state = mutableStateOf(ExpenseDetailState())
    val state: State<ExpenseDetailState> = _state

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiFlow = MutableSharedFlow<BudgetScreenFlow>()
    val uiFlow = _uiFlow.asSharedFlow()

    init {
        getArgs()
    }

    private fun getArgs() = viewModelScope.launch {
        val details = savedStateHandle.get<String>(EXPENSE_DETAIL_ARGUMENT_KEY)?.fromJson<ExpenseDetailNavArgs>()
        _state.value = ExpenseDetailState(
            expenseId = details?.id,
            amount = details?.amount,
            category = Category.dummies().find { it.id == details?.categoryId },
            photoId = details?.photoId,
            more = details?.moreDetails,
            date = LocalDate.parse(details?.date)
        )
        Timber.i("photoId: ${details?.photoId}")
        savedStateHandle.clearSavedStateProvider(EXPENSE_DETAIL_ARGUMENT_KEY)
        loading(false)
        details?.photoId?.let { getInvoiceImage(it) }
    }

    private fun getInvoiceImage(id: String) = viewModelScope.launch {
        val response = storageRepository.getFile(id)
        _state.value = state.value.copy(photo = response)
    }

    fun deleteExpense(id: String, fileId: String?) = viewModelScope.launch {
        loading(true)
        try {
            deleteExpenseUseCase(id, fileId)
            _uiFlow.emit(BudgetScreenFlow.SnackBar("Expense deleted successfully!"))
            delay(1000)
            _uiFlow.emit(BudgetScreenFlow.NavigateUp)
            loading(false)
        } catch (e: Exception) {
            if (e is IOException) {
                _uiEvent.emit(Error(StringResource(R.string.internet_error_msg)))
            } else Timber.e(e)
            loading(false)
        }
    }

    private fun loading(loading: Boolean) {
        _state.value = state.value.copy(loading = loading)
    }

}