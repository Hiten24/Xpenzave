package com.hcapps.xpenzave.data.local_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hcapps.xpenzave.data.local_source.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExpense(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenses(list: List<ExpenseEntity>)

    @Query("SELECT * FROM expense WHERE id = :id")
    suspend fun getExpense(id: String): ExpenseEntity

    @Query("SELECT * FROM expense where month = :month AND year = :year")
    fun getExpenses(month: Int, year: Int): Flow<List<ExpenseEntity>>

}