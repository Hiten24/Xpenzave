package com.hcapps.xpenzave.util

import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_DETAIL_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_FILTER_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.OAUTH2_SEGMENT_ARG_KEY
import timber.log.Timber

sealed class Screen(val route: String) {

    object Authentication: Screen(route = "authentication_screen?$OAUTH2_SEGMENT_ARG_KEY={$OAUTH2_SEGMENT_ARG_KEY}") {
        fun withArgs(segment: String): String {
            Timber.i("screen segment: $segment")
            return "authentication_screen?$OAUTH2_SEGMENT_ARG_KEY=$segment"
        }
    }

    object Home: Screen(route = "home_screen")

    object Settings: Screen(route = "settings_screen")

    object Stats: Screen(route = "stats_screen?$EXPENSE_FILTER_ARGUMENT_KEY={$EXPENSE_FILTER_ARGUMENT_KEY}") {
        fun withArgs(filters: String) = "stats_screen?$EXPENSE_FILTER_ARGUMENT_KEY=$filters"
    }

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

    object Filter: Screen(route = "filter?$EXPENSE_FILTER_ARGUMENT_KEY={$EXPENSE_FILTER_ARGUMENT_KEY}") {
        fun withArgs(filters: String) = "filter?$EXPENSE_FILTER_ARGUMENT_KEY=$filters"
    }

}
