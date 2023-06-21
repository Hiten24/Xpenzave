package com.hcapps.xpenzave.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hcapps.xpenzave.presentation.add_expense.AddExpense
import com.hcapps.xpenzave.presentation.auth.AuthenticationScreen
import com.hcapps.xpenzave.presentation.auth.LoginScreen
import com.hcapps.xpenzave.presentation.auth.RegisterScreen
import com.hcapps.xpenzave.presentation.auth.forgot_password.ForgotPasswordScreen
import com.hcapps.xpenzave.presentation.calendar.CalendarScreen
import com.hcapps.xpenzave.presentation.compare.CompareSelector
import com.hcapps.xpenzave.presentation.compare.result.CompareResult
import com.hcapps.xpenzave.presentation.edit_budget.EditBudgetScreen
import com.hcapps.xpenzave.presentation.expense_detail.ExpenseDetailNavArgs
import com.hcapps.xpenzave.presentation.expense_detail.ExpenseDetailScreen
import com.hcapps.xpenzave.presentation.filter.FilterScreen
import com.hcapps.xpenzave.presentation.home.HomeScreen
import com.hcapps.xpenzave.presentation.on_board.OnBoardScreen
import com.hcapps.xpenzave.presentation.settings.SettingsScreen
import com.hcapps.xpenzave.presentation.stats.StatsScreen
import com.hcapps.xpenzave.util.Screen
import com.hcapps.xpenzave.util.UiConstants

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(
        route = Screen.Authentication.route
    ) {
        AuthenticationScreen(navigateToHome)
    }
}

fun NavGraphBuilder.loginRoute(
    navigateToRegister: () -> Unit,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToForgotPassword: () -> Unit
) {
    composable(route = Screen.Login.route) {
        LoginScreen(
            navigateToHome = navigateToHome,
            register = navigateToRegister,
            navigateUp = navigateUp,
            forgotPassword = navigateToForgotPassword
        )
    }
}

fun NavGraphBuilder.registerRoute(navigateToLogin: () -> Unit, navigateToHome: () -> Unit, navigateUp: () -> Unit) {
    composable(route = Screen.Register.route) {
        RegisterScreen(
            navigateToHome = navigateToHome,
            login = navigateToLogin,
            navigateUp = navigateUp
        )
    }
}

fun NavGraphBuilder.homeRoute(
    paddingValues: PaddingValues,
    navigateToEditBudget: (date: String, budgetId: String) -> Unit,
    navigateToExpenseLog: () -> Unit,
    navigateToDetails: (details: ExpenseDetailNavArgs) -> Unit,
    navigateToAddExpense: () -> Unit
) {
    composable(route = Screen.Home.route) { backStackEntry ->
        HomeScreen(
            paddingValues = paddingValues,
            editBudget = { date, budgetId ->
                backStackEntry.savedStateHandle.remove<Double>(UiConstants.BUDGET_VALUE_ARGUMENT_KEY)
                navigateToEditBudget(date, budgetId)
            },
            expenseLog = navigateToExpenseLog,
            expenseDetail = navigateToDetails,
            addExpense = navigateToAddExpense
        )
    }
}

fun NavGraphBuilder.settingsRoute(navigateToAuth: () -> Unit, paddingValues: PaddingValues) {
    composable(route = Screen.Settings.route) {
        SettingsScreen(navigateToAuth = navigateToAuth, paddingValues = paddingValues)
    }
}

fun NavGraphBuilder.statsRoute(
    paddingValues: PaddingValues,
    navigateToCompare: () -> Unit,
    navigateToFilter: (appliedFilters: Array<String>) -> Unit,
    navigateToDetails: (details: ExpenseDetailNavArgs) -> Unit
) {
    composable(
        route = Screen.Stats.route
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
            navArgument(name = UiConstants.EDIT_BUDGET_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = false
                defaultValue = ""
            },
            navArgument(name = UiConstants.EDIT_BUDGET_BUDGET_ID_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        EditBudgetScreen(
            navigateUp = navigateUp
        )
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
    onNavigateUp: () -> Unit,
    onDeleteExpense: (String) -> Unit
) {
    composable(
        route = Screen.ExpenseDetail.route,
        arguments = listOf(
            navArgument(name = UiConstants.EXPENSE_DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        ExpenseDetailScreen(navigateUp = onNavigateUp, onDeleteExpense)
    }
}

fun NavGraphBuilder.filter(
    onNavigateUp: () -> Unit,
    navigateToStateScreen: (filters: Array<String>) -> Unit
) {
    composable(
        route = Screen.Filter.route
    ) {
        FilterScreen(
            navigateUp = onNavigateUp,
            applyFilter = navigateToStateScreen
        )
    }
}

fun NavGraphBuilder.onBoard(navigateToLogin: () -> Unit, navigateToRegister: () -> Unit) {
    composable(route = Screen.OnBoard.route) {
        OnBoardScreen(
            logIn =  navigateToLogin,
            createAccount =  navigateToRegister
        )
    }
}

fun NavGraphBuilder.forgotPasswordRoute() {
    composable(route = Screen.ForgotPassword.route) {
        ForgotPasswordScreen()
    }
}