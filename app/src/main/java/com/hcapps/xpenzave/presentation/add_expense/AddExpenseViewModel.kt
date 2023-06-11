package com.hcapps.xpenzave.presentation.add_expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.APP_WRITE_DATE_TIME_FORMAT
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    val amount = mutableStateOf("")
    val moreDetails = mutableStateOf("")
    val date = mutableStateOf(LocalDateTime.now())
    val addBillToEachMonth = mutableStateOf(false)

    init {
//        getCategories()
    }

    fun addExpense() = viewModelScope.launch {
        val expense = ExpenseData(
            amount = amount.value.toDoubleOrNull() ?: 0.0,
            details = moreDetails.value,
            categoryId = Calendar.getInstance().timeInMillis.toString(),
            date = date.value.format(DateTimeFormatter.ofPattern(APP_WRITE_DATE_TIME_FORMAT)),
            day = date.value.dayOfMonth,
            month = date.value.monthValue,
            year = date.value.year
        )
        databaseRepository.addExpense(expense)

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