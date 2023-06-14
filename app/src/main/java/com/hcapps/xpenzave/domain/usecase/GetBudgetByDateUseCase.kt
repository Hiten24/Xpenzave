package com.hcapps.xpenzave.domain.usecase

import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.data.source.remote.repository.database.FakeDatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.budget.BudgetDomainData
import com.hcapps.xpenzave.domain.model.budget.toBudgetDomainData
import java.time.LocalDate
import javax.inject.Inject

class GetBudgetByDateUseCase @Inject constructor(
//    private val databaseRepository: DatabaseRepository
    private val databaseRepository: FakeDatabaseRepository
) {

    suspend fun execute(date: LocalDate): BudgetDomainData? {
        return when (val response = databaseRepository.getBudgetByDate(date)) {
            is RequestState.Success -> {
                response.data?.toBudgetDomainData()
            }
            is RequestState.Error -> {
                throw response.error
            }
            else -> null
        }
    }

}