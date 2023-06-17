package com.hcapps.xpenzave.data.local_source.repository

import com.hcapps.xpenzave.data.local_source.dao.ExpenseDao
import com.hcapps.xpenzave.data.local_source.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
): LocalDatabaseRepository {

    override suspend fun insertExpenses(list: List<ExpenseEntity>) {
        return expenseDao.insertExpenses(list)
    }

    override fun getExpenses(date: LocalDate): Flow<List<ExpenseEntity>> {
        return expenseDao.getExpenses(month = date.monthValue, year = date.year)
    }
}