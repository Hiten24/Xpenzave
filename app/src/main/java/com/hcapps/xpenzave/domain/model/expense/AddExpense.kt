package com.hcapps.xpenzave.domain.model.expense

import com.hcapps.xpenzave.domain.model.Response
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.util.serverDateToLocalDateTime
import io.appwrite.ID
import java.time.LocalDateTime
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

fun Response<ExpenseData>.toExpenseDomainData() = ExpenseDomainData(
    id = id,
    category = data.categoryId,
    date = LocalDateTime.parse(serverDateToLocalDateTime(data.date)),
    amount = data.amount,
    photoId = data.photo,
    moreDetail = data.details
)

private val fakeMoreDetails = listOf(
"Coffee from a cafe",
"Groceries from the supermarket",
"Dinner at a restaurant",
"Taxi ride",
"Movie tickets for a group",
"Online shopping order",
"Gym membership fee",
"Utility bill payment",
"Clothing purchase",
"Concert ticket"
)

fun fakeExpenseData(date: Int, month: Int = 6, year: Int = 2023) = ExpenseData(
    amount = Random.nextInt(100, 2000).toDouble(),
    photo = null,
    details = fakeMoreDetails.getOrNull(Random.nextInt(0, fakeMoreDetails.size - 1)) ?: "",
    date = "$date-06-$year 11:11:00.000",
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
