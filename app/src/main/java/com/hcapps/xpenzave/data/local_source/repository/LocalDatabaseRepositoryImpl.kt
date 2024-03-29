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

    override fun getExpenses(date: LocalDate, filters: List<String>): Flow<List<ExpenseEntity>> {
        Timber.i("month: ${date.month}, year: ${date.year}")
        val monthAndYear = "${String.format("%02d", date.monthValue)} ${date.year}"
        return expenseDao.getExpenses(monthAndYear = monthAndYear, filters, filters.size)
    }

    override fun getBudget(date: LocalDate): Flow<BudgetEntity?> {
        Timber.i("local db date: ${date.month.name} ${date.year}")
        return budgetDao.getBudgetByDate(date.monthValue, date.year)
    }

    override suspend fun addBudget(budgetEntity: BudgetEntity) {
        budgetDao.upsertBudget(budgetEntity)
    }

    override suspend fun addExpense(expense: ExpenseEntity) {
        expenseDao.insertExpenses(listOf(expense))
    }

    override suspend fun deleteExpense(id: String) {
        expenseDao.deleteExpense(id)
    }

    override suspend fun dropDb() {
        expenseDao.drop()
        budgetDao.drop()
    }
}