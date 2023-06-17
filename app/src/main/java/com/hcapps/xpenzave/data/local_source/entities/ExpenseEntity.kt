package com.hcapps.xpenzave.data.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import java.time.LocalDate

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey
    val id: String,
    val amount: Double,
    val details: String = "",
    val category: String,
    val addThisExpenseToEachMonth: Boolean,
    val photo: String?,
    val day: Int,
    val month: Int,
    val year: Int
) {
    fun toExpenseDomainData() = ExpenseDomainData(
        id = id,
        category = category,
        date = LocalDate.of(year, month, day),
        amount = amount,
        photoId = photo,
        moreDetail = details
    )
}
