package com.hcapps.xpenzave.domain.model.expense

import com.hcapps.xpenzave.domain.model.Response
import io.appwrite.ID
import java.time.LocalDate
import java.time.Month

data class ExpenseData(
    val amount: Double,
    val photo: String? = null,
    val details: String = "",
    val date: String = "10-06-2023 11:11:00.000",
    val categoryId: String = ID.unique(),
    val addThisExpenseToEachMonth: Boolean = false,
    val day: Int = 10,
    val month: Int = 6,
    val year: Int = 2023
)

fun Response<ExpenseData>.toExpenseDomainData() = ExpenseDomainData(
    id = id,
    category = data.categoryId,
    date = LocalDate.of(data.year, Month.of(data.month), data.day),
    amount = data.amount
)
