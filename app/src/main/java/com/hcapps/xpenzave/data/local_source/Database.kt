package com.hcapps.xpenzave.data.local_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hcapps.xpenzave.data.local_source.dao.BudgetDao
import com.hcapps.xpenzave.data.local_source.dao.CategoryDao
import com.hcapps.xpenzave.data.local_source.dao.ExpenseDao
import com.hcapps.xpenzave.data.local_source.entities.BudgetEntity
import com.hcapps.xpenzave.data.local_source.entities.CategoryEntity
import com.hcapps.xpenzave.data.local_source.entities.ExpenseEntity

@Database(
    entities = [ExpenseEntity::class, BudgetEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class XpenzaveDatabase: RoomDatabase() {

    abstract fun budgetDao(): BudgetDao

    abstract fun categoryDao(): CategoryDao

    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "xpenzave_db"
    }

}
