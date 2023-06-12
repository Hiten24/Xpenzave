package com.hcapps.xpenzave.presentation.expense_log

import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import java.time.LocalDate

data class ExpenseLogState(
    val date: LocalDate = LocalDate.now(),
    val expenses: Map<LocalDate, List<ExpenseDomainData>> = emptyMap()
)
