package com.hcapps.xpenzave.presentation.add_expense.state

import android.net.Uri

sealed class AddExpenseEvent {
    data class AmountChange(val amount: String): AddExpenseEvent()
    data class CategoryChange(val category: String): AddExpenseEvent()
    data class DetailsChange(val detail: String): AddExpenseEvent()
    data class PhotoChange(val photo: Uri?, val Path: String): AddExpenseEvent()
    object AddBillEachMonthChange: AddExpenseEvent()
    object ClearPhoto: AddExpenseEvent()
}