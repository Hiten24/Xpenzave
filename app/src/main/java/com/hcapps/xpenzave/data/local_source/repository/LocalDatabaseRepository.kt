package com.hcapps.xpenzave.data.local_source.repository

import com.hcapps.xpenzave.data.local_source.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LocalDatabaseRepository {

    suspend fun insertExpenses(list: List<ExpenseEntity>)

    fun getExpenses(date: LocalDate): Flow<List<ExpenseEntity>>

}