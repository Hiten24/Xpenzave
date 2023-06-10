package com.hcapps.xpenzave.presentation.add_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
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

}