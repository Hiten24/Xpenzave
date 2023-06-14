package com.hcapps.xpenzave.domain.model.expense

import com.hcapps.xpenzave.domain.model.Response
import com.hcapps.xpenzave.domain.model.category.Category
import io.appwrite.ID
import java.time.LocalDate
import java.time.Month
import kotlin.random.Random

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

fun fakeExpenseData(date: Int, month: Int = 6, year: Int = 2023) = ExpenseData(
    amount = Random.nextInt(100, 2000).toDouble(),
    photo = null,
    details = "",
    date = "$date-0$month-$year 11:11:00.000",
    categoryId = Category.dummies().random().id,
    addThisExpenseToEachMonth = false,
    day = date,
    month = month,
    year = year
)

fun fakeExpenses(size: Int = 10): List<ExpenseData> {
    return IntRange(1, size).map {
        fakeExpenseData(Random.nextInt(1, 30))
    }.sortedBy { it.day }
}

fun Response<ExpenseData>.toExpenseDomainData() = ExpenseDomainData(
    id = id,
    category = data.categoryId,
    date = LocalDate.of(data.year, Month.of(data.month), data.day),
    amount = data.amount
)
