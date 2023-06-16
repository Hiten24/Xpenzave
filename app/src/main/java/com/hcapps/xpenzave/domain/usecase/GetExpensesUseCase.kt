package com.hcapps.xpenzave.domain.usecase

import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
//    private val databaseRepository: FakeDatabaseRepository
) {
    suspend fun execute(date: LocalDate, filter: List<String>): List<ExpenseDomainData> {
        return when (val response = databaseRepository.getExpensesByMonth(date, filter)) {
            is RequestState.Success -> {
                val expenses = response.data
                expenses
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                throw(response.error)
            }
            else -> { emptyList() }
        }
    }
}