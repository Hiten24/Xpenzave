package com.hcapps.xpenzave.presentation.stats

import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import java.time.LocalDate

data class StatsState(
    val date: LocalDate = LocalDate.now(),
    val loading: Boolean = true,
    val emptyScreen: Boolean = false,
//    var tabScreen: Int = TAB_EXPENSE_LOG,
    var tabScreen: Int = TAB_GENERAL,
//    val expenses: Map<LocalDate, List<ExpenseDomainData>>? = null
)
