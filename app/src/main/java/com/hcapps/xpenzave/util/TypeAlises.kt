package com.hcapps.xpenzave.util

import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import java.time.LocalDate

typealias ExpenseLogType = Map<LocalDate, List<ExpenseDomainData>>