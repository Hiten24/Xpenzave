package com.hcapps.xpenzave.data.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hcapps.xpenzave.data.local_source.converter.DateConverter
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import java.time.LocalDateTime


@Entity(tableName = "expense")
@TypeConverters(DateConverter::class)
data class ExpenseEntity(
    @PrimaryKey
    val id: String,
    val amount: Double,
    val details: String = "",
    val category: String,
    val addThisExpenseToEachMonth: Boolean,
    val photo: String?,
    val date: LocalDateTime,
    val day: Int,
    val month: Int,
    val year: Int
) {
    fun toExpenseDomainData() = ExpenseDomainData(
        id = id,
        category = category,
        date = date,
        amount = amount,
        photoId = photo,
        moreDetail = details
    )
}
