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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.PasswordState
import com.hcapps.xpenzave.presentation.core.UiEventReceiver
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.presentation.core.component.input.XpenzaveTextField

@Composable
fun RegisterScreen(
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit,
    login: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val state by viewModel.state

    viewModel.uiEvent.UiEventReceiver()

    Scaffold(
        topBar = {
            AuthTopBar(
                title = stringResource(id = R.string.register_title),
                subtitle = stringResource(id = R.string.register_subtitle),
                actionText = stringResource(id = R.string.register_action_text),
                onNavigation = navigateUp,
                onAction = login
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

            RegisterContent(
                email = state.email,
                onEmailChanged = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
                password = state.password,
                onPasswordChanged = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
                emailError = state.emailError,
                passwordError = state.passwordError,
                register = { viewModel.onEvent(AuthEvent.Register(navigateToHome)) },
                loading = state.loading,
                confirmPassword = state.confirmPassword,
                onConfirmPasswordChanged = { viewModel.onEvent(AuthEvent.ConfirmPasswordChanged(it)) },
                confirmPasswordError = state.confirmPasswordError,
                passwordState = state.createPasswordState
            )

        }
    }

}

@Composable
fun RegisterContent(
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: String? = null,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: String? = null,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
    confirmPasswordError: String? = null,
    loading: Boolean = false,
    register: () -> Unit,
    passwordState: PasswordState? = null
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
            imeAction = ImeAction.Next,
            error = passwordError
        )

        passwordState?.let {
            val checkIcon = stringResource(id = R.string.check_unicode)
            val bulletIcon = stringResource(id = R.string.bullet_point_unicode)
            Text(
                text = stringResource(id = R.string.password_rule_title),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_1,
                    if (passwordState.shouldBeMin8Max20Char) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_2,
                    if (passwordState.shouldHaveALowerCase) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_3,
                    if (passwordState.shouldHaveAUpperCase) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_4,
                    if (passwordState.shouldHaveANumberOrAcceptableCharacter) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        XpenzaveTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            label = stringResource(id = R.string.confirm_password),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            error = confirmPasswordError
        )

        Spacer(modifier = Modifier.height(12.dp))

        XpenzaveButton(
            title = "Register",
            enabled = email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
            loading = loading,
            onClickOfButton = register
        )

    }
}