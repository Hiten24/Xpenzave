package com.hcapps.xpenzave.domain.model.expense

import java.time.LocalDate

data class ExpenseDomainData(
    val id: String,
    val category: String,
    val date: LocalDate,
    val amount: Double
) {
    companion object {
        fun dummy() = ExpenseDomainData(
            id = "",
            category = "",
            date = LocalDate.now(),
            amount = 100.0
        )
    }
}
