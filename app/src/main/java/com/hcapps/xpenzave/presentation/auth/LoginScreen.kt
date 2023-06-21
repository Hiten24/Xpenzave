package com.hcapps.xpenzave.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.presentation.core.component.input.XpenzaveTextField

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    register: () -> Unit,
    navigateUp: () -> Unit,
    forgotPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.state

    Scaffold(
        topBar = {
            AuthTopBar(
                title = stringResource(id = R.string.login_title),
                subtitle = stringResource(id = R.string.login_subtitle),
                actionText = stringResource(id = R.string.login_action_text),
                onNavigation = navigateUp,
                onAction = register
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(vertical = 32.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            LoginContent(
                email = state.email,
                onEmailChanged = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
                password = state.password,
                onPasswordChanged = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
                emailError = state.emailError,
                passwordError = state.passwordError,
                login = { viewModel.onEvent(AuthEvent.Login) },
                loading = state.loading,
                forgotPassword = forgotPassword
            )

        }
    }

}

@Composable
fun LoginContent(
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: String? = null,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: String? = null,
    loading: Boolean = false,
    forgotPassword: () -> Unit,
    login: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        XpenzaveTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = stringResource(id = R.string.e_mail),
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            error = emailError
        )

        XpenzaveTextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = stringResource(id = R.string.set_password),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            error = passwordError,
            action = login
        )

        TextButton(modifier = Modifier.align(Alignment.End), onClick = forgotPassword) {
            Text(
                text = "Forgot Password",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))

        XpenzaveButton(
            title = "Log in",
            enabled = email.isNotEmpty() && password.isNotEmpty(),
            loading = loading,
            onClickOfButton = login
        )

    }
}