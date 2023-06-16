package com.hcapps.xpenzave.presentation.expense_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.data.remote_source.repository.storage.StorageRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.presentation.edit_budget.BudgetScreenFlow
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_DETAIL_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.extensions.fromJson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
//    private val databaseRepository: FakeDatabaseRepository,
    private val databaseRepository: DatabaseRepository,
    private val storageRepository: StorageRepository,
): ViewModel() {

    private val _state = mutableStateOf(ExpenseDetailState())
    val state: State<ExpenseDetailState> = _state

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
        savedStateHandle.clearSavedStateProvider(EXPENSE_DETAIL_ARGUMENT_KEY)
        loading(false)
        details?.photoId?.let { getInvoiceImage(it) }
    }

    private fun getInvoiceImage(id: String) = viewModelScope.launch {
        val response = storageRepository.getFile(id)
        _state.value = state.value.copy(photo = response)
    }

    fun deleteExpense(id: String, onSuccess: (id: String) -> Unit) = viewModelScope.launch {
        loading(true)
        when (val response = databaseRepository.removeExpense(id)) {
            is RequestState.Success -> {
                _uiFlow.emit(BudgetScreenFlow.SnackBar("Expense deleted successfully!"))
                savedStateHandle[EXPENSE_ID] = id
                delay(1000L) // to show SnackBar
                onSuccess(id)
                _uiFlow.emit(BudgetScreenFlow.NavigateUp)
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
        _state.value = state.value.copy(loading = loading)
    }

}