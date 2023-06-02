package com.hcapps.xpenzave.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        SettingsHeader()
        SettingsContent()
    }
}

@Composable
fun SettingsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.primary
                    )
                )
            )
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        SettingsTopBar()
        Spacer(modifier = Modifier.height(32.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "E-mail",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "john.snow@gmail.com",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
fun SettingsTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        OutlinedButton(onClick = { /*TODO*/ }, shape = Shapes().small) {
            Text(text = "Log Out", color = Color.White)
        }
    }
}

@Composable
fun SettingsContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 38.dp)
    ) {
        SettingItem(title = "Change Password", openable = true) {

        }
        Spacer(modifier = Modifier.height(10.dp))
        SettingItem(title = "Use Face ID", openable = false) {
            
        }
        Spacer(modifier = Modifier.height(10.dp))
        SettingItem(title = "Currency", openable = false) {
            
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    openable: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(vertical = 12.dp))
        if (openable) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow Forward"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewSettingsHeader() {
    SettingsHeader()
}

@Preview
@Composable
fun PreviewSettingsTopBar() {
    SettingsTopBar()
}

