package com.hcapps.xpenzave.data.local_source.repository

import com.hcapps.xpenzave.data.local_source.dao.BudgetDao
import com.hcapps.xpenzave.data.local_source.dao.ExpenseDao
import com.hcapps.xpenzave.data.local_source.entities.BudgetEntity
import com.hcapps.xpenzave.data.local_source.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val budgetDao: BudgetDao
): LocalDatabaseRepository {

    override suspend fun insertExpenses(list: List<ExpenseEntity>) {
        return expenseDao.insertExpenses(list)
    }

    override fun getExpenses(date: LocalDate): Flow<List<ExpenseEntity>> {
        return expenseDao.getExpenses(month = date.monthValue, year = date.year)
    }

    override fun getBudget(date: LocalDate): Flow<BudgetEntity?> {
        Timber.i("local db date: ${date.month.name} ${date.year}")
        return budgetDao.getBudgetByDate(date.monthValue, date.year)
    }

    override suspend fun addBudget(budgetEntity: BudgetEntity) {
        budgetDao.upsertBudget(budgetEntity)
    }
}