package com.hcapps.xpenzave.data.local_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hcapps.xpenzave.data.local_source.entities.BudgetEntity

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBudget(budget: BudgetEntity)

    @Query("SELECT * FROM budget WHERE id = :id")
    suspend fun getBudget(id: String): BudgetEntity

}