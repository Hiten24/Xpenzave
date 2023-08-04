package com.hcapps.xpenzave.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
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

        navigation(startDestination = Screen.OnBoard.route, route = Screen.AuthNavigation.route) {

            loginRoute(
                navigateToRegister = {
                    navController.popBackStack()
                    navController.navigate(Screen.Register.route)
                },
                navigateToHome = {
                    navController.navigate(Screen.MainNavigation.route) {
                        popUpTo(Screen.AuthNavigation.route) { inclusive = true }
                    }
                },
                navigateUp = { navController.navigateUp() },
                navigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
            )

            registerRoute(
                navigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                },
                navigateToHome = {
                    navController.navigate(Screen.MainNavigation.route) {
                        popUpTo(Screen.MainNavigation.route) { inclusive = true }
                    }
                },
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

        navigation(Screen.Home.route, route = Screen.MainNavigation.route) {

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
                    navController.navigate(Screen.AuthNavigation.route) {
                        popUpTo(Screen.MainNavigation.route) { inclusive = true }
                    }
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

        }
    }
}