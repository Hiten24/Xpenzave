package com.hcapps.xpenzave.data.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val icon: String
)
