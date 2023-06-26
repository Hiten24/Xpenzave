package com.hcapps.xpenzave.domain.usecase.budget

import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.budget.toBudgetDomainData
import java.time.LocalDate
import javax.inject.Inject

class GetBudgetByDateUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {

    suspend fun execute(date: LocalDate) {
        when (val response = databaseRepository.getBudgetByDate(date)) {
            is RequestState.Success -> {
                val budget = response.data?.toBudgetDomainData()
                budget?.toBudgetEntity()?.let { localDatabaseRepository.addBudget(it) }
            }
            is RequestState.Error -> throw response.error
            else -> {}
        }
    }

}