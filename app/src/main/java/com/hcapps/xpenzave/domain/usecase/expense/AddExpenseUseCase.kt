package com.hcapps.xpenzave.domain.usecase.expense

import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.domain.model.expense.toExpenseDomainData
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {
    suspend operator fun  invoke(expense: ExpenseData): Boolean {
        return when(val response = databaseRepository.addExpense(expense)) {
            is RequestState.Success -> {
                val localData = response.data.toExpenseDomainData().toExpenseEntity()
                localDatabaseRepository.addExpense(localData)
                true
            }
            is RequestState.Error -> throw response.error
            else -> false
        }
    }
}