package com.hcapps.xpenzave.domain.model.budget

import com.hcapps.xpenzave.data.local_source.entities.BudgetEntity
import java.time.LocalDate
import java.util.UUID

data class BudgetDomainData(
    val id: String? = null,
    val date: LocalDate,
    val amount: Double
) {
    fun toBudgetEntity() = BudgetEntity(
        id = id ?: UUID.randomUUID().toString(),
        month = date.monthValue,
        year = date.year,
        amount = amount
    )
}
