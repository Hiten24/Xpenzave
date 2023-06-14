package com.hcapps.xpenzave.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hcapps.xpenzave.presentation.add_expense.AddExpense
import com.hcapps.xpenzave.presentation.auth.AuthenticationScreen
import com.hcapps.xpenzave.presentation.calendar.CalendarScreen
import com.hcapps.xpenzave.presentation.compare.CompareSelector
import com.hcapps.xpenzave.presentation.compare.result.CompareResult
import com.hcapps.xpenzave.presentation.edit_budget.EditBudgetScreen
import com.hcapps.xpenzave.presentation.expense_detail.ExpenseDetailScreen
import com.hcapps.xpenzave.presentation.filter.FilterScreen
import com.hcapps.xpenzave.presentation.home.HomeScreen
import com.hcapps.xpenzave.presentation.settings.SettingsScreen
import com.hcapps.xpenzave.presentation.stats.StateScreen
import com.hcapps.xpenzave.util.Screen
import com.hcapps.xpenzave.util.UiConstants

@Composable
fun XpenzaveNavGraph(
    startDestination: String,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = startDestination) {

        authenticationRoute {
            navController.popBackStack()
            navController.navigate(Screen.Settings.route)
        }

        homeRoute(
            paddingValues,
            navigateToEditBudget = { monthYear ->
                navController.navigate(Screen.EditBudget.passMonthYear(monthYear))
            },
            navigateToExpenseLog = {
                navController.navigate(Screen.Stats.route)
            }
        )

        settingsRoute(navigateToAuth = {
            navController.popBackStack()
            navController.navigate(Screen.Authentication.route)
        })

        statsRoute(
            paddingValues,
            navigateToCompare = { navController.navigate(Screen.CompareSelector.route) },
            navigateToCalendar = { navController.navigate(Screen.Calendar.route) },
            navigateToFilter = { navController.navigate(Screen.Filter.route) },
        )

        editBudget(
            navigateUp = { navController.navigateUp() }
        )

        addExpense(
            navigateUp = { navController.navigateUp() }
        )

        compareSelector(
            onNavigateUp = { navController.navigateUp() },
            navigateToResult = { navController.navigate(Screen.CompareResult.route) }
        )

        compareResult(
            onNavigateUp = { navController.navigateUp() }
        )

        calendar(onNavigateUp = { navController.navigateUp() })

        expenseDetail(onNavigateUp = { navController.navigateUp() })

        filter(onNavigateUp = {
            navController.navigateUp()
        })

    }
}

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(route = Screen.Authentication.route) {
        AuthenticationScreen(navigateToHome)
    }
}

fun NavGraphBuilder.homeRoute(
    paddingValues: PaddingValues,
    navigateToEditBudget: (String) -> Unit,
    navigateToExpenseLog: () -> Unit
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            paddingValues = paddingValues,
            editBudget = navigateToEditBudget,
            expenseLog = navigateToExpenseLog
        )
    }
}

fun NavGraphBuilder.settingsRoute(navigateToAuth: () -> Unit) {
    composable(route = Screen.Settings.route) {
        SettingsScreen(navigateToAuth = navigateToAuth)
    }
}

fun NavGraphBuilder.statsRoute(
    paddingValues: PaddingValues,
    navigateToCompare: () -> Unit,
    navigateToCalendar: () -> Unit,
    navigateToFilter: () -> Unit,
) {
    composable(route = Screen.Stats.route) {
        StateScreen(
            paddingValues,
            navigateToCompare,
            navigateToCalendar,
            navigateToFilter,
        )
    }
}


fun NavGraphBuilder.editBudget(
    navigateUp: () -> Unit
) {
    composable(
        route = Screen.EditBudget.route,
        arguments = listOf(navArgument(name = UiConstants.EDIT_BUDGET_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = false
            defaultValue = "" }
        )
    ) {
            EditBudgetScreen(navigateUp = navigateUp)
    }
}

fun NavGraphBuilder.addExpense(
    navigateUp: () -> Unit
) {
    composable(route = Screen.AddExpense.route) {
        AddExpense(navigateUp = navigateUp)
    }
}

fun NavGraphBuilder.compareSelector(
    onNavigateUp: () -> Unit,
    navigateToResult: () -> Unit
) {
    composable(route = Screen.CompareSelector.route) {
        CompareSelector(
            navigateCompareResult = navigateToResult,
            navigateUp = onNavigateUp
        )
    }
}

fun NavGraphBuilder.compareResult(onNavigateUp: () -> Unit) {
    composable(route = Screen.CompareResult.route) {
        CompareResult(onNavigateUp)
    }
}

fun NavGraphBuilder.calendar(onNavigateUp: () -> Unit) {
    composable(route = Screen.Calendar.route) {
        CalendarScreen(onNavigateUp)
    }
}

fun NavGraphBuilder.expenseDetail(
    onNavigateUp: () -> Unit
) {
    composable(route = Screen.ExpenseDetail.route) {
        ExpenseDetailScreen(navigateUp = onNavigateUp)
    }
}

fun NavGraphBuilder.filter(onNavigateUp: () -> Unit) {
    composable(route = Screen.Filter.route) {
        FilterScreen(navigateUp = onNavigateUp)
    }
}