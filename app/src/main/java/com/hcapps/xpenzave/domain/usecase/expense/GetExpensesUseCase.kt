package com.hcapps.xpenzave.domain.usecase.expense

import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {
    suspend fun execute(date: LocalDate, filter: List<String>) {
        when (val response = databaseRepository.getExpensesByMonth(date, filter)) {
            is RequestState.Success -> {
                val expenses = response.data
                val expenseEntities = expenses.map { it.toExpenseEntity() }
                Timber.i("date: $date")
                Timber.i("expenses entity: $expenseEntities")
                localDatabaseRepository.insertExpenses(expenseEntities)
            }
            is RequestState.Error -> { throw(response.error) }
            else -> {  }
        }
    }
}