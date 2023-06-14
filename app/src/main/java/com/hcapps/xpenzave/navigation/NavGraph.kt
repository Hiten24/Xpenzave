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
import com.hcapps.xpenzave.presentation.expense_detail.ExpenseDetailNavArgs
import com.hcapps.xpenzave.presentation.expense_detail.ExpenseDetailScreen
import com.hcapps.xpenzave.presentation.filter.FilterScreen
import com.hcapps.xpenzave.presentation.home.HomeScreen
import com.hcapps.xpenzave.presentation.settings.SettingsScreen
import com.hcapps.xpenzave.presentation.stats.StatsScreen
import com.hcapps.xpenzave.util.Screen
import com.hcapps.xpenzave.util.UiConstants
import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_DETAIL_ARGUMENT_KEY
import io.appwrite.extensions.toJson

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
            navigateToEditBudget = { monthYear, budgetId ->
                navController.navigate(Screen.EditBudget.passArgs(monthYear, budgetId))
            },
            navigateToExpenseLog = {
                navController.navigate(Screen.Stats.withArgs(""))
            }
        )

        settingsRoute(navigateToAuth = {
            navController.popBackStack()
            navController.navigate(Screen.Authentication.route)
        })

        statsRoute(
            paddingValues,
            navigateToCompare = { navController.navigate(Screen.CompareSelector.route) },
            navigateToFilter = { appliedFilters ->
                navController.navigate(Screen.Filter.withArgs(appliedFilters.toJson()))
            },
            navigateToDetails = { navController.navigate(Screen.ExpenseDetail.passArgs(it.toJson())) }
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

        filter(
            onNavigateUp = { navController.navigateUp() },
            navigateToStateScreen = { filters ->
                navController.popBackStack()
                navController.navigate(Screen.Stats.withArgs(filters.toJson()))
            }
        )

    }
}

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(route = Screen.Authentication.route) {
        AuthenticationScreen(navigateToHome)
    }
}

fun NavGraphBuilder.homeRoute(
    paddingValues: PaddingValues,
    navigateToEditBudget: (date: String, budgetId: String) -> Unit,
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
    navigateToFilter: (appliedFilters: Array<String>) -> Unit,
    navigateToDetails: (details: ExpenseDetailNavArgs) -> Unit
) {
    composable(
        route = Screen.Stats.route,
        arguments = listOf(
            navArgument(UiConstants.EXPENSE_FILTER_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        StatsScreen(
            paddingValues = paddingValues,
            navigateToCompare = navigateToCompare,
            navigateToFilter = navigateToFilter,
            navigateToDetails = navigateToDetails
        )
    }
}


fun NavGraphBuilder.editBudget(
    navigateUp: () -> Unit
) {
    composable(
        route = Screen.EditBudget.route,
        arguments = listOf(
            navArgument(name = EDIT_BUDGET_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = false
                defaultValue = ""
            },
            navArgument(name = EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
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
    composable(
        route = Screen.ExpenseDetail.route,
        arguments = listOf(
            navArgument(name = EXPENSE_DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        ExpenseDetailScreen(navigateUp = onNavigateUp)
    }
}

fun NavGraphBuilder.filter(
    onNavigateUp: () -> Unit,
    navigateToStateScreen: (filters: Array<String>) -> Unit
) {
    composable(
        route = Screen.Filter.route,
        arguments = listOf(
            navArgument(UiConstants.EXPENSE_FILTER_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        FilterScreen(
            navigateUp = onNavigateUp,
            applyFilter = navigateToStateScreen
        )
    }
}