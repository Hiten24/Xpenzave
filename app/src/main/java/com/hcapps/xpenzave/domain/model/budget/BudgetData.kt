package com.hcapps.xpenzave.domain.model.budget

import com.hcapps.xpenzave.domain.model.Response
import java.time.LocalDate
import kotlin.random.Random

data class BudgetData(
    val month: Int,
    val year: Int,
    val amount: Double
)

fun Response<BudgetData>.toBudgetDomainData() = BudgetDomainData(
    id = id,
    date = LocalDate.of(data.year, data.month, 1),
    amount = data.amount
)

fun fakeBudgetData(month: Int, year: Int) = BudgetData(
    month = month,
    year = year,
    amount = Random.nextInt(1, 10) * 1000.0
)

fun fakeBudgetListData(): List<BudgetData> {
    return IntRange(1, 12).map {
        fakeBudgetData(it, 2023)
    }
}