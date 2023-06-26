package com.hcapps.xpenzave.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hcapps.xpenzave.navigation.XpenzaveNavGraph
import com.hcapps.xpenzave.presentation.core.UiEventReceiver
import com.hcapps.xpenzave.presentation.core.component.XpenzaveScaffold
import com.hcapps.xpenzave.ui.theme.XpenzaveTheme
import com.hcapps.xpenzave.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private var keepSplashOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { keepSplashOn }

        setContent {

            val mainViewModel = hiltViewModel<MainViewModel>()
            val state by mainViewModel.sessionState

            mainViewModel.uiEvent.UiEventReceiver()

            LaunchedEffect(key1 = state) {
                keepSplashOn = state.loading
            }

            XpenzaveTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                XpenzaveScaffold(
                    onClickOfItem = { route -> navController.navigate(route) },
                    backStackEntry = backStackEntry
                ) { padding ->
                    if (!state.loading) {
                        XpenzaveNavGraph(
                            startDestination = state.startDestination ?: Screen.OnBoard.route,
                            navController = navController,
                            paddingValues = padding,
                            onDataLoaded = { keepSplashOn = false }
                        )
                    }
                }
            }
        }

    }

}
