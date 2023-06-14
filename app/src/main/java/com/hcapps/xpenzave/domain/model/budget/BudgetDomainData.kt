package com.hcapps.xpenzave.domain.model.budget

data class BudgetDomainData(
    val id: String,
    val month: Int,
    val year: Int,
    val amount: Double
)
