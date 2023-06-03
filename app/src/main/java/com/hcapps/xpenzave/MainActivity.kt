package com.hcapps.xpenzave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.hcapps.xpenzave.navigation.XpenzaveNavGraph
import com.hcapps.xpenzave.ui.theme.XpenzaveTheme
import com.hcapps.xpenzave.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XpenzaveTheme {
                val navController = rememberNavController()
                XpenzaveNavGraph(
                    startDestination = Screen.Authentication.route,
                    navController = navController
                )
            }
        }
    }
}