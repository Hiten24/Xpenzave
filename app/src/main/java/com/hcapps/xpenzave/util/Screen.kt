package com.hcapps.xpenzave.util

import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_DETAIL_ARGUMENT_KEY

sealed class Screen(val route: String) {

    object Authentication: Screen(route = "authentication_screen")

    object Home: Screen(route = "home_screen")

    object Settings: Screen(route = "settings_screen")

    object Stats: Screen(route = "stats_screen")

    object EditBudget: Screen(route = "edit_budget?$EDIT_BUDGET_ARGUMENT_KEY={$EDIT_BUDGET_ARGUMENT_KEY}&" +
            "$EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY={$EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY}") {
        fun passArgs(monthYear: String, budgetId: String) =
            "edit_budget?$EDIT_BUDGET_ARGUMENT_KEY=$monthYear" +
                "&$EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY=$budgetId"
    }

    object AddExpense: Screen(route = "add_expense")

    object CompareSelector: Screen(route = "compare_month_selector")

    object CompareResult: Screen(route = "compare_result")

    object Calendar: Screen(route = "calendar")

    object ExpenseDetail: Screen(route = "expense_detail?$EXPENSE_DETAIL_ARGUMENT_KEY={$EXPENSE_DETAIL_ARGUMENT_KEY}") {
        fun passArgs(detail: String) = "expense_detail?$EXPENSE_DETAIL_ARGUMENT_KEY=$detail"
    }

    object Filter: Screen(route = "filter")

    object Login: Screen(route = "login")

    object Register: Screen(route = "register")

    object OnBoard: Screen(route = "on_board")

    object ForgotPassword: Screen(route = "forgot_password")

    object ChangePassword: Screen(route = "change_password")

}
