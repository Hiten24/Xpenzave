package com.hcapps.xpenzave.domain.model.expense

import com.hcapps.xpenzave.domain.model.category.Category
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

data class ExpenseDomainData(
    val id: String,
    val category: String,
    val date: LocalDate,
    val amount: Double
) {
    companion object {

        fun dummy(date: LocalDate = LocalDate.now()) = ExpenseDomainData(
            id = UUID.randomUUID().toString(),
            category = Category.dummies().random().id,
            date = date,
            amount = Random.nextDouble(100.0, 5000.0)
        )
    }
}
