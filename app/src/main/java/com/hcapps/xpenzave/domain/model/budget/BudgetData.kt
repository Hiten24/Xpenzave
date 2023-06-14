package com.hcapps.xpenzave.domain.model.budget

import com.hcapps.xpenzave.domain.model.Response
import kotlin.random.Random

data class BudgetData(
    val month: Int,
    val year: Int,
    val amount: Double
)

fun Response<BudgetData>.toBudgetDomainData() = BudgetDomainData(
    id = id,
    month = data.month,
    year = data.year,
    amount = data.amount
)

fun fakeBudgetData(month: Int, year: Int) = BudgetData(
    month = month,
    year = year,
    amount = Random.nextInt(1, 5) * 1000.0
)