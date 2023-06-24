package com.hcapps.xpenzave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.navigation.XpenzaveNavGraph
import com.hcapps.xpenzave.presentation.core.component.XpenzaveScaffold
import com.hcapps.xpenzave.ui.theme.XpenzaveTheme
import com.hcapps.xpenzave.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStoreService

    private var keepSplashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { keepSplashOn }

        setContent {
            XpenzaveTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = true) {
                    val destination = getStartDestination(dataStore) {
                        keepSplashOn = false
                    }
                    navController.popBackStack()
                    navController.navigate(destination)
                }
                val backStackEntry = navController.currentBackStackEntryAsState()
                XpenzaveScaffold(
                    onClickOfItem = { route -> navController.navigate(route) },
                    backStackEntry = backStackEntry
                ) { padding ->
                    XpenzaveNavGraph(
                        startDestination = Screen.OnBoard.route,
                        navController = navController,
                        paddingValues = padding
                    )
                }
            }
        }

    }

}

suspend fun getStartDestination(dataStore: DataStoreService, onResult: () -> Unit): String {
    return withContext(Dispatchers.IO) {
        val user = dataStore.getUserFlow().first()
        return@withContext if (user.userId.isEmpty()) {
            onResult()
            Screen.OnBoard.route
        }
        else {
            onResult()
            Screen.Home.route
        }
    }
}
