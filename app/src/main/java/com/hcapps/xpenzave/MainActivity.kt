package com.hcapps.xpenzave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
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
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStoreService

    // receives verify or failed redirected from oauth2
    private lateinit var intentDataSegment: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intentDataSegment = intent.data?.lastPathSegment ?: ""
        Timber.i("intent: ${intent.data}")
        Timber.i("intent: ${intent.data?.lastPathSegment}")
        setContent {
            XpenzaveTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = Unit) {
                    val destination = getStartDestination(dataStore, intentDataSegment)
//                    navController.popBackStack()
//                    navController.navigate(destination)
                }
                val backStackEntry = navController.currentBackStackEntryAsState()
                XpenzaveScaffold(
                    onClickOfItem = { route -> navController.navigate(route) },
                    backStackEntry = backStackEntry
                ) { padding ->
                    XpenzaveNavGraph(
                        startDestination = Screen.Authentication.withArgs(intentDataSegment),
                        navController = navController,
                        paddingValues = padding
                    )
                }
            }
        }

    }

}

suspend fun getStartDestination(dataStore: DataStoreService, segment: String): String {
    return withContext(Dispatchers.IO) {
        val user = dataStore.getUserFlow().first()
        return@withContext if (user.userId.isEmpty()) Screen.Authentication.withArgs(segment)
        else Screen.Home.route
    }
}
