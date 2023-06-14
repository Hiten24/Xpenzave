package com.hcapps.xpenzave.presentation.expense_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.data.source.remote.repository.storage.StorageRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.domain.usecase.GetSampleImage
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_DETAIL_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.extensions.fromJson
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val databaseRepository: DatabaseRepository,
    private val storageRepository: StorageRepository,
    private val getSampleImage: GetSampleImage
): ViewModel() {

    private val _state = mutableStateOf(ExpenseDetailState())
    val state: State<ExpenseDetailState> = _state

    init {
//        getExpense()
        getArgs()
    }

    private fun getArgs() {
        val details = savedStateHandle.get<String>(EXPENSE_DETAIL_ARGUMENT_KEY)?.fromJson<ExpenseDetailNavArgs>()
        _state.value = state.value.copy(
            amount = details?.amount,
            category = Category.dummies().find { it.id == details?.categoryId },
            photoId = details?.photoId,
            more = details?.moreDetails,
            loading = false,
            date = LocalDate.parse(details?.date)
        )
        details?.photoId?.let { getInvoiceImage(it) }
    }

    private fun getInvoiceImage(id: String) = viewModelScope.launch {
        val response = storageRepository.getFile(id)
        _state.value = state.value.copy(photo = response)
    }

    private fun getExpense() = viewModelScope.launch {
        _state.value = state.value.copy(loading = true)
        when (val response = databaseRepository.getExpense("6486df83a1ac27f70170")) {
            is RequestState.Success -> {
                val data = response.data.data
                _state.value = ExpenseDetailState(
                    amount = data.amount.toString(),
                    category = Category.dummies().find { it.id == data.categoryId },
                    photoId = data.photo,
                    more = data.details,
                    loading = false
                )
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                _state.value = state.value.copy(loading = false)
            }
            else -> {}
        }
    }

}