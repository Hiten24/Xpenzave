package com.hcapps.xpenzave.util

sealed class Screen(val route: String) {

    object Authentication: Screen(route = "authentication_screen")

    object Home: Screen(route = "home_screen")

    object Settings: Screen(route = "settings_screen")

    object Stats: Screen(route = "stats_screen")

    object Expense: Screen(route = "expense_screen")

    object AddExpense: Screen(route = "add_expense")

}
