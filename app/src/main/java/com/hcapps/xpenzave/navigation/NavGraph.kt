package com.hcapps.xpenzave.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hcapps.xpenzave.presentation.auth.AuthenticationScreen
import com.hcapps.xpenzave.presentation.expense.ExpenseScreen
import com.hcapps.xpenzave.presentation.home.HomeScreen
import com.hcapps.xpenzave.presentation.settings.SettingsScreen
import com.hcapps.xpenzave.presentation.stats.StateScreen
import com.hcapps.xpenzave.util.Screen

@Composable
fun XpenzaveNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(navController = navController, startDestination = startDestination) {

        authenticationRoute {
            navController.popBackStack()
            navController.navigate(Screen.Settings.route)
        }

        homeRoute()

        settingsRoute(navigateToAuth = {
            navController.popBackStack()
            navController.navigate(Screen.Authentication.route)
        })

        statsRoute()

        expenseRoute()

    }
}

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(route = Screen.Authentication.route) {
        AuthenticationScreen(navigateToHome)
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
        HomeScreen()
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

fun NavGraphBuilder.expenseRoute() {
    composable(route = Screen.Expense.route) {
        ExpenseScreen()
    }
}