package com.hcapps.xpenzave.presentation.expense_log

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExpenseLogViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private val _state = mutableStateOf(ExpenseLogState())
    val state: State<ExpenseLogState> = _state

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        when (val response = databaseRepository.getCategoriesByMont(state.value.date.monthValue)) {
            is RequestState.Success -> {
                _state.value = state.value.copy(expenses = response.data.groupBy { it.date })
            }
            is RequestState.Error -> {
                Timber.e(response.error)
            }
            else -> {}
        }
    }

}