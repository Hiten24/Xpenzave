package com.hcapps.xpenzave.presentation.expense_detail

data class ExpenseDetailNavArgs(
    val amount: String,
    val categoryId: String,
    val photoId: String?,
    val moreDetails: String,
    val date: String
)
