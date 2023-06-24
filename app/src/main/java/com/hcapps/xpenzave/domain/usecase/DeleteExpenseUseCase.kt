package com.hcapps.xpenzave.domain.usecase

import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.domain.model.RequestState
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return when (val response = databaseRepository.removeExpense(id)) {
            is RequestState.Success -> {
                localDatabaseRepository.deleteExpense(id)
                true
            }
            is RequestState.Error -> {
                throw response.error
            }
            else -> false
        }
    }
}