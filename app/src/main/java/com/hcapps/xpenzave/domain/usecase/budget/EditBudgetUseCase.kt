package com.hcapps.xpenzave.domain.usecase.budget

import com.hcapps.xpenzave.data.local_source.entities.BudgetEntity
import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.budget.BudgetData
import javax.inject.Inject

class EditBudgetUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {

    suspend operator fun invoke(budgetId: String? = null, budget: BudgetData) {
        val response = if (budgetId == null) {
            databaseRepository.createBudget(budget)
        } else {
            databaseRepository.updateBudget(budgetId, budget)
        }
        when (response) {
            is RequestState.Success -> {
                val data = response.data
                localDatabaseRepository.addBudget(BudgetEntity(
                    data.id,
                    data.data.month,
                    data.data.year,
                    data.data.amount
                ))
            }
            is RequestState.Error -> {
                throw response.error
            }
            else -> {}
        }
    }

}