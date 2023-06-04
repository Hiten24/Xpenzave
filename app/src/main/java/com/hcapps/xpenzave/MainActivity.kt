package com.hcapps.xpenzave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.hcapps.xpenzave.navigation.XpenzaveNavGraph
import com.hcapps.xpenzave.ui.theme.XpenzaveTheme
import com.hcapps.xpenzave.util.Screen
import com.hcapps.xpenzave.util.SettingsDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XpenzaveTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = Unit) {
                    navController.popBackStack()
                    navController.navigate(getStartDestination(dataStore))
                }
                XpenzaveNavGraph(
                    startDestination = Screen.Authentication.route,
                    navController = navController
                )
            }
        }
    }
}

suspend fun getStartDestination(dataStore: SettingsDataStore): String {
    return withContext(Dispatchers.IO) {
        val isLoggedIn = dataStore.getBoolean(SettingsDataStore.SETTINGS_IS_LOGGED_IN_KEY)
        return@withContext if (!isLoggedIn) Screen.Authentication.route
        else Screen.Settings.route
    }
}