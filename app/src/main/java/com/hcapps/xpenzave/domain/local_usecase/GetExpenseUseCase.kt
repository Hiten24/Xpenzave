package com.hcapps.xpenzave.domain.local_usecase

import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class LocalExpenseUseCase @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository
) {
    operator fun invoke(
        date: LocalDate,
        filters: List<String> = emptyList()
    ): Flow<List<ExpenseDomainData>> {
        return localDatabaseRepository.getExpenses(date, filters)
            .map { it.map { expense -> expense.toExpenseDomainData() } }
    }

}