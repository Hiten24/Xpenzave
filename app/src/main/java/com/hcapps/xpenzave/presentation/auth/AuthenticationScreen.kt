package com.hcapps.xpenzave.presentation.auth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.auth.event.AuthUiEventFlow.Message
import com.hcapps.xpenzave.presentation.auth.event.AuthUiEventFlow.OAuth2Success
import com.hcapps.xpenzave.presentation.core.component.input.XpenzaveTextField
import com.hcapps.xpenzave.ui.theme.ButtonHeight
import com.hcapps.xpenzave.util.Constant.AUTH_LOGIN_SCREEN
import com.hcapps.xpenzave.util.Constant.AUTH_REGISTER_SCREEN
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    navigateToHome: () -> Unit,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.authScreenState

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is Message -> Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).show()
                is OAuth2Success -> navigateToHome()
            }
        }
    }

    Scaffold(
        topBar = {
            AuthTopBar(
                title = if (state.authState == AUTH_LOGIN_SCREEN) "Log in to Xpenzave" else "Ready to track your Expenses?",
                actionText = if (state.authState == AUTH_LOGIN_SCREEN) "Join now" else "Sing in",
                onNavigation = { /*TODO*/ },
                onAction = {
                    val screen = if (state.authState == AUTH_LOGIN_SCREEN) AUTH_REGISTER_SCREEN else AUTH_LOGIN_SCREEN
                    viewModel.onEvent(AuthEvent.SwitchAuthScreen(screen))
                }
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

            RegisterMiddleComponent(
                email = state.email,
                onEmailChanged = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
                password = state.password,
                onPasswordChanged = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
                emailError = state.emailError,
                passwordError = state.passwordError,
                confirmPassword = "",
                onConfirmPasswordChange = { viewModel.onEvent(AuthEvent.ConfirmPasswordChanged(it)) },
                confirmPasswordError = "",
                screen = state.authState
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterBottomComponent(
                onClickOfRegisterButton = {
                    if (state.authState == AUTH_LOGIN_SCREEN) {
                        viewModel.login(onSuccess = { navigateToHome() })
                    } else {
                        viewModel.registerUser(onSuccess = { navigateToHome() })
                    }
                },
                onClickOfFaceBook = {
                    viewModel.loginWithOath2(
                        context as ComponentActivity,
                        provider = "facebook",
                        onSuccess = { Toast.makeText(context, "logged in successfully", Toast.LENGTH_SHORT).show() }
                    )
                },
                onClickOfGoogle = {
                    viewModel.loginWithOath2(
                        context as ComponentActivity,
                        provider = "google",
                        onSuccess = { Toast.makeText(context, "logged in successfully", Toast.LENGTH_SHORT).show() }
                    )
                },
                buttonTitle = if (state.authState == AUTH_LOGIN_SCREEN) stringResource(R.string.login) else stringResource(R.string.register),
                loadingState = state.loading,
                buttonEnabled = state.email.isNotEmpty() && state.password.isNotEmpty()
            )
        }

    }
}

@Composable
fun RegisterMiddleComponent(
     email: String,
     onEmailChanged: (String) -> Unit,
     emailError: String? = null,
     password: String,
     onPasswordChanged: (String) -> Unit,
     passwordError: String? = null,
     confirmPassword: String,
     onConfirmPasswordChange: (String) -> Unit,
     confirmPasswordError: String? = null,
     screen: Int
) {

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {

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
            label = if (screen == AUTH_REGISTER_SCREEN) stringResource(id = R.string.set_password)
                    else stringResource(id = R.string.password),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            error = passwordError
        )

        if (screen == AUTH_REGISTER_SCREEN) {
            XpenzaveTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = stringResource(id = R.string.confirm_password),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                error = confirmPasswordError
            )
        }

    }
}

@Composable
fun RegisterBottomComponent(
    modifier: Modifier = Modifier,
    onClickOfRegisterButton: () -> Unit,
    buttonEnabled: Boolean = false,
    onClickOfGoogle: () -> Unit,
    onClickOfFaceBook: () -> Unit,
    buttonTitle: String,
    loadingState: Boolean
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onClickOfRegisterButton()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonHeight),
            shape = Shapes().small,
            enabled = !loadingState && buttonEnabled
        ) {
            if (!loadingState) {
                Text(
                    text = buttonTitle,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                )
            }
            if (loadingState) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp,
                    strokeCap = StrokeCap.Round
                )
            }
        }

    }
}