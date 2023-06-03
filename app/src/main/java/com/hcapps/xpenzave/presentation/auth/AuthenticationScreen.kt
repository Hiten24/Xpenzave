package com.hcapps.xpenzave.presentation.auth

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.ui.theme.ButtonHeight
import com.hcapps.xpenzave.util.Constant.AUTH_LOGIN_SCREEN
import com.hcapps.xpenzave.util.Constant.AUTH_REGISTER_SCREEN

@Composable
fun AuthenticationScreen(
    navigateToHome: () -> Unit,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var email by viewModel.emailState
    var password by viewModel.passwordState
    var screenState by viewModel.authScreenState

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
                screenState = AUTH_LOGIN_SCREEN
            },
            onClickOfRegister = {
                screenState = AUTH_REGISTER_SCREEN
            },
            screenState = screenState
        )

        RegisterMiddleComponent(
            email = email,
            onEmailChanged = { email = it },
            password = password,
            onPasswordChanged = { password = it }
        )

        RegisterBottomComponent(
            onClickOfRegisterButton = {
                if (screenState == AUTH_LOGIN_SCREEN) {
                    viewModel.login(
                        onSuccess = { Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show() },
                        onError = { Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show() }
                    )
                } else {
                    viewModel.registerUser(
                        onSuccess = { Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show() },
                        onError = { Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show() }
                    )
                }
            },
            onClickOfFaceBook = {
                Toast.makeText(context, "Facebook Register", Toast.LENGTH_SHORT).show()
            },
            onClickOfGoogle = {
                Toast.makeText(context, "Google Register", Toast.LENGTH_SHORT).show()
            },
            buttonTitle = if (screenState == AUTH_LOGIN_SCREEN) "Log In" else "Register"
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
                Text(text = "E-Mail")
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
                Text(text = "Set Password")
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
    buttonTitle: String
) {

    val loadingState by remember { mutableStateOf(false) }

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
            text = "or continue with",
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
                    text = "FaceBook",
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
                    text = "Google",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}