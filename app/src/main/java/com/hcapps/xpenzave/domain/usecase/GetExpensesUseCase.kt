package com.hcapps.xpenzave.domain.usecase

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
                localDatabaseRepository.insertExpenses(expenseEntities)
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                throw(response.error)
            }
            else -> {  }
        }
    }
}