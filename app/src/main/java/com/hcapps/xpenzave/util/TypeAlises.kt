package com.hcapps.xpenzave.util

import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import java.time.LocalDateTime

typealias ExpenseLogType = Map<LocalDateTime, List<ExpenseDomainData>>