package com.hcapps.xpenzave.presentation.edit_budget

sealed class BudgetScreenFlow {
    data class SnackBar(val message: String, val isError: Boolean = false): BudgetScreenFlow()
    object NavigateUp: BudgetScreenFlow()
}
