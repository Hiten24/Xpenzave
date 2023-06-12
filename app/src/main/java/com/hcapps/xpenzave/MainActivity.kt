package com.hcapps.xpenzave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hcapps.xpenzave.data.datastore.DataSore
import com.hcapps.xpenzave.navigation.XpenzaveNavGraph
import com.hcapps.xpenzave.presentation.core.component.XpenzaveScaffold
import com.hcapps.xpenzave.ui.theme.XpenzaveTheme
import com.hcapps.xpenzave.util.Screen
//import com.hcapps.xpenzave.util.SettingsDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataSore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XpenzaveTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = Unit) {
                    navController.popBackStack()
                    navController.navigate(getStartDestination(dataStore))
                }
                val backStackEntry = navController.currentBackStackEntryAsState()
                XpenzaveScaffold(
                    onClickOfItem = { route -> navController.navigate(route) },
                    backStackEntry = backStackEntry
                ) { padding ->
                    XpenzaveNavGraph(
                        startDestination = Screen.Authentication.route,
                        navController = navController,
                        paddingValues = padding
                    )
                }
            }
        }
    }
}

suspend fun getStartDestination(dataStore: DataSore): String {
    return withContext(Dispatchers.IO) {
        val user = dataStore.getUser().first()
        return@withContext if (user.userId == null) Screen.Authentication.route
        else Screen.Home.route
    }
}
