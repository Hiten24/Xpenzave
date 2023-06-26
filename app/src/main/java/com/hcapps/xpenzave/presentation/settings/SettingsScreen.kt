package com.hcapps.xpenzave.presentation.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.core.UiEventReceiver
import com.hcapps.xpenzave.presentation.settings.SettingsEvent.LogOut
import com.hcapps.xpenzave.ui.theme.ButtonHeight
import com.hcapps.xpenzave.ui.theme.primaryGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    changePassword: () -> Unit
) {

    val state by viewModel.state

    viewModel.uiEventFlow.UiEventReceiver()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                modifier = Modifier.background(primaryGradient()),
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
            )
        }
    ) { topBarPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(topBarPadding)
            .padding(paddingValues)
        ) {
            SettingsHeader(state.email)
            Spacer(modifier = Modifier.weight(1f))
            SettingsContent(
                logOut = { viewModel.onEvent(LogOut(navigateToAuth)) },
                changePassword = changePassword,
                logOutLoading = state.logOutLoading
            )
        }
    }

}

@Composable
fun SettingsHeader(
    email: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(primaryGradient())
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.e_mail),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = email,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    logOut: () -> Unit,
    changePassword: () -> Unit,
    logOutLoading: Boolean
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        SettingItem(title = stringResource(id = R.string.change_password_title), onClick = changePassword) {
            Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
        }
        Spacer(modifier = Modifier.weight(1f))
        LogOutButton(logOut = logOut, loading = logOutLoading)
    }
}

@Composable
fun LogOutButton(logOut: () -> Unit, loading: Boolean) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.error),
        onClick = logOut
    ) {
        if (!loading) {
            Text(
                text = "Log Out",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    endCompose: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(vertical = 12.dp))
        endCompose()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewSettingsHeader() {
    SettingsHeader("test.account@gmail.com")
}