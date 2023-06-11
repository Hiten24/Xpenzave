package com.hcapps.xpenzave.presentation.auth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.ui.theme.ButtonHeight
import com.hcapps.xpenzave.util.Constant.AUTH_LOGIN_SCREEN
import com.hcapps.xpenzave.util.Constant.AUTH_REGISTER_SCREEN
import kotlinx.coroutines.flow.collectLatest

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
                is UIEvent.Error -> Toast.makeText(context, event.error.message, Toast.LENGTH_SHORT).show()
                is UIEvent.ShowMessage -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UIEvent.Loading -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterHeader(
            onClickOfLogin = {
                viewModel.onEvent(AuthEvent.SwitchAuthScreen(AUTH_LOGIN_SCREEN))
            },
            onClickOfRegister = {
                viewModel.onEvent(AuthEvent.SwitchAuthScreen(AUTH_REGISTER_SCREEN))
            },
            screenState = state.authState
        )

        RegisterMiddleComponent(
            email = state.email,
            onEmailChanged = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
            password = state.password,
            onPasswordChanged = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) }
        )

        RegisterBottomComponent(
            onClickOfRegisterButton = {
                if (state.authState == AUTH_LOGIN_SCREEN) {
                    viewModel.login(
                        onSuccess = { navigateToHome() }
                    )
                } else {
                    viewModel.registerUser(
                        onSuccess = { navigateToHome() }
                    )
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
            loadingState = state.loading
        )
    }
}

@Composable
fun RegisterHeader(
    screenState: Int,
    onClickOfLogin: () -> Unit,
    onClickOfRegister: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.headlineMedium,
            color = if (screenState == AUTH_LOGIN_SCREEN)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                onClickOfLogin()
            }
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            color = if (screenState == AUTH_REGISTER_SCREEN)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                onClickOfRegister()
            }
        )
    }
}

@Composable
fun RegisterMiddleComponent(
     email: String,
     onEmailChanged: (String) -> Unit,
     password: String,
     onPasswordChanged: (String) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = {
                Text(text = stringResource(R.string.e_mail))
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = {
                Text(text = stringResource(R.string.set_password))
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation()
        )

    }
}

@Composable
fun RegisterBottomComponent(
    onClickOfRegisterButton: () -> Unit,
    onClickOfGoogle: () -> Unit,
    onClickOfFaceBook: () -> Unit,
    buttonTitle: String,
    loadingState: Boolean
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
//                loadingState = true
                onClickOfRegisterButton()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonHeight),
            shape = Shapes().small,
            enabled = !loadingState
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.or_continue_with),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            FilledTonalButton(
                onClick = { onClickOfFaceBook() },
                shape = Shapes().small,
                enabled = !loadingState,
                colors = ButtonDefaults
                    .buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                modifier = Modifier
                    .height(ButtonHeight)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.facebook),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            FilledTonalButton(
                onClick = { onClickOfGoogle() },
                shape = Shapes().small,
                enabled = !loadingState,
                colors = ButtonDefaults
                    .buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                modifier = Modifier
                    .height(ButtonHeight)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.google),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}