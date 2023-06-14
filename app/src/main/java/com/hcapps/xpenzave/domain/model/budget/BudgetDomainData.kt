package com.hcapps.xpenzave.domain.model.budget

import java.time.LocalDate

data class BudgetDomainData(
    val id: String? = null,
    val date: LocalDate,
    val amount: Double
)
