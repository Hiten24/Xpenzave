package com.hcapps.xpenzave.presentation.add_expense.state

import java.time.LocalDateTime

data class AddExpenseState(
    val amount: String = "",
    val amountError: String? = null,
    val details: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val eachMonth: Boolean = false,
    val category: String = ""
)
