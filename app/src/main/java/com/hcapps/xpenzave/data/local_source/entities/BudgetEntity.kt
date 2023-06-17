package com.hcapps.xpenzave.data.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class BudgetEntity(
    @PrimaryKey
    val id: String,
    val month: Int,
    val year: Int,
    val amount: Int
)