package com.hcapps.xpenzave.presentation.edit_budget

sealed class BudgetScreenFlow {
    data class SnackBar(val message: String): BudgetScreenFlow()
    object NavigateUp: BudgetScreenFlow()
}
