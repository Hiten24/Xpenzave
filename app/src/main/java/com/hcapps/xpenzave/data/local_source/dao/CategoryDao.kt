package com.hcapps.xpenzave.data.local_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hcapps.xpenzave.data.local_source.entities.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategory(category: CategoryEntity)

    @Query("SELECT * FROM category")
    suspend fun getCategories(): List<CategoryEntity>

}