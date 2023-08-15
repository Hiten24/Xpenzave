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

//    @Query("SELECT * FROM expense where month = :month AND year = :year AND :filterSize = 0 OR category IN (:filters)")
    @Query("SELECT * FROM expense WHERE strftime('%m %Y', date) = :monthAndYear AND (:filterSize = 0 OR category IN (:filters))")
    fun getExpenses(monthAndYear: String, filters: List<String>, filterSize: Int): Flow<List<ExpenseEntity>>
//    fun getExpenses(month: Int, year: Int, filters: List<String>, filterSize: Int): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteExpense(id: String)

    @Query("DELETE FROM expense")
    suspend fun drop()

}