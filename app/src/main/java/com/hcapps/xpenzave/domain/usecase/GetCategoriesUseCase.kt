package com.hcapps.xpenzave.domain.usecase

import com.hcapps.xpenzave.data.source.remote.repository.database.FakeDatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
//    private val databaseRepository: DatabaseRepository
    private val databaseRepository: FakeDatabaseRepository
) {
    suspend fun execute(date: LocalDate): Map<LocalDate, List<ExpenseDomainData>> {
        return when (val response = databaseRepository.getExpensesByMonth(date)) {
            is RequestState.Success -> {
                val expenses = response.data.groupBy { it.date }
                Timber.i("expenses: $expenses")
                expenses
            }
            is RequestState.Error -> {
                Timber.e(response.error)
                throw(response.error)
            }
            else -> {
                emptyMap()
            }
        }
    }
}