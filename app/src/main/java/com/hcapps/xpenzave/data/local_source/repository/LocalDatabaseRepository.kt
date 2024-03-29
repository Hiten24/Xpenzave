package com.hcapps.xpenzave.data.local_source.repository

import com.hcapps.xpenzave.data.local_source.entities.BudgetEntity
import com.hcapps.xpenzave.data.local_source.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LocalDatabaseRepository {

    suspend fun insertExpenses(list: List<ExpenseEntity>)

    fun getExpenses(date: LocalDate, filters: List<String>): Flow<List<ExpenseEntity>>

    fun getBudget(date: LocalDate): Flow<BudgetEntity?>

    suspend fun addBudget(budgetEntity: BudgetEntity)

    suspend fun addExpense(expense: ExpenseEntity)

    suspend fun deleteExpense(id: String)

    suspend fun dropDb()

}