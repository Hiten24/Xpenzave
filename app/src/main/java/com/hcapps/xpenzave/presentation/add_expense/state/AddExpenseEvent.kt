package com.hcapps.xpenzave.presentation.add_expense.state

sealed class AddExpenseEvent {
    data class EnterAmount(val amount: String): AddExpenseEvent()
    data class SelectCategory(val category: String): AddExpenseEvent()
    data class EnterDetails(val detail: String): AddExpenseEvent()
    object ChangeAddBillEachMonth: AddExpenseEvent()
}