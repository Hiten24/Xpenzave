package com.hcapps.xpenzave.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hcapps.xpenzave.util.Screen
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_FILTER_ARGUMENT_KEY
import io.appwrite.extensions.toJson

@Composable
fun XpenzaveNavGraph(
    startDestination: String,
    navController: NavHostController,
    paddingValues: PaddingValues,
    onDataLoaded: () -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination) {

        authenticationRoute {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        }

        homeRoute(
            paddingValues,
            navigateToEditBudget = { monthYear, budgetId ->
                navController.navigate(Screen.EditBudget.withArgs(monthYear, budgetId))
            },
            navigateToExpenseLog = {
                navController.navigate(Screen.Stats.route)
            },
            navigateToDetails = { navController.navigate(Screen.ExpenseDetail.withArgs(it.toJson())) },
            navigateToAddExpense = { navController.navigate(Screen.AddExpense.route) },
            onDataLoaded = onDataLoaded
        )

        settingsRoute(
            navigateToAuth = {
                navController.navigate(Screen.Login.route)
            },
            paddingValues,
            navigateToChangePassword = {
                navController.navigate(Screen.ChangePassword.route)
            }
        )

        statsRoute(
            paddingValues,
            navigateToCompare = { navController.navigate(Screen.CompareSelector.route) },
            navigateToFilter = {
                navController.navigate(Screen.Filter.withArgs(it))
            },
            navigateToDetails = { navController.navigate(Screen.ExpenseDetail.withArgs(it.toJson())) }
        )

        editBudgetRoute(
            navigateUp = { navController.navigateUp() }
        )

        addExpenseRoute(
            navigateUp = { navController.navigateUp() }
        )

        compareSelectorRoute(
            onNavigateUp = { navController.navigateUp() },
            navigateToResult = { navController.navigate(Screen.CompareResult.route) }
        )

        compareResultRoute(
            onNavigateUp = { navController.navigateUp() }
        )

        calendarRoute(onNavigateUp = { navController.navigateUp() })

        expenseDetailRoute(
            onNavigateUp = { navController.navigateUp() }
        )

        filterRoute(
            onNavigateUp = {
                navController.previousBackStackEntry?.savedStateHandle?.set(EXPENSE_FILTER_ARGUMENT_KEY, it.toJson())
                navController.navigateUp()
            }
        )

        loginRoute(
            navigateToRegister = { navController.navigate(Screen.Register.route) },
            navigateToHome = { navController.navigate(Screen.Home.route) },
            navigateUp = { navController.navigateUp() },
            navigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
        )

        registerRoute(
            navigateToLogin = { navController.navigate(Screen.Login.route) },
            navigateToHome = { navController.navigate(Screen.Home.route) },
            navigateUp = { navController.navigateUp() }
        )

        onBoardRoute(
            navigateToLogin = { navController.navigate(Screen.Login.route) },
            navigateToRegister = { navController.navigate(Screen.Register.route) },
            onDataLoaded = onDataLoaded
        )

        forgotPasswordRoute(navigateUp = { navController.navigateUp() })

        changePasswordRoute(navigateUp = { navController.navigateUp() })

    }
}