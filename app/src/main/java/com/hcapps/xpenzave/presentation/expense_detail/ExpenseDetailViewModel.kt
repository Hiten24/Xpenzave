package com.hcapps.xpenzave.presentation.expense_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.category.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private val _state = mutableStateOf<ExpenseDetailState>(ExpenseDetailState())
    val state: State<ExpenseDetailState> = _state

    init {
        getExpense()
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