package com.hcapps.xpenzave.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hcapps.xpenzave.presentation.add_expense.AddExpense
import com.hcapps.xpenzave.presentation.auth.AuthenticationScreen
import com.hcapps.xpenzave.presentation.edit_budget.EditBudgetScreen
import com.hcapps.xpenzave.presentation.home.HomeScreen
import com.hcapps.xpenzave.presentation.settings.SettingsScreen
import com.hcapps.xpenzave.presentation.stats.StateScreen
import com.hcapps.xpenzave.util.Screen

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
            navigateToEditBudget = {
                navController.navigate(Screen.EditBudget.route)
            }
        )

        settingsRoute(navigateToAuth = {
            navController.popBackStack()
            navController.navigate(Screen.Authentication.route)
        })

        statsRoute()

        editBudget(
            navigateUp = { navController.navigateUp() }
        )

        addExpense(
            navigateUp = { navController.navigateUp() }
        )

    }
}

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(route = Screen.Authentication.route) {
        AuthenticationScreen(navigateToHome)
    }
}

fun NavGraphBuilder.homeRoute(paddingValues: PaddingValues, navigateToEditBudget: () -> Unit) {
    composable(route = Screen.Home.route) {
        HomeScreen(paddingValues, navigateToEditBudget)
    }
}

fun NavGraphBuilder.settingsRoute(navigateToAuth: () -> Unit) {
    composable(route = Screen.Settings.route) {
        SettingsScreen(navigateToAuth = navigateToAuth)
    }
}

fun NavGraphBuilder.statsRoute() {
    composable(route = Screen.Stats.route) {
        StateScreen()
    }
}


fun NavGraphBuilder.editBudget(
    navigateUp: () -> Unit
) {
    composable(route = Screen.EditBudget.route) {
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