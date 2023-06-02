package com.hcapps.xpenzave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hcapps.xpenzave.presentation.auth.RegisterScreen
import com.hcapps.xpenzave.ui.theme.XpenzaveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XpenzaveTheme {
                RegisterScreen()
            }
        }
    }
}