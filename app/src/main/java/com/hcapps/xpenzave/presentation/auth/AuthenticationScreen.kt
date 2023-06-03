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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.ui.theme.ButtonHeight

@Composable
fun AuthenticationScreen(
    navigateToHome: () -> Unit,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var email by viewModel.emailState
    var password by viewModel.passwordState

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
                Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
            },
            onClickOfRegister = {
                Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show()
            }
        )

        RegisterMiddleComponent(
            email = email,
            onEmailChanged = { email = it },
            password = password,
            onPasswordChanged = { password = it }
        )

        RegisterBottomComponent(
            onClickOfRegisterButton = {
                viewModel.registerUser {
                    Toast.makeText(context, "Fill all the require field to register", Toast.LENGTH_SHORT).show()
                }
            },
            onClickOfFaceBook = {
                Toast.makeText(context, "Facebook Register", Toast.LENGTH_SHORT).show()
            },
            onClickOfGoogle = {
                Toast.makeText(context, "Google Register", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun RegisterHeader(
    onClickOfLogin: () -> Unit,
    onClickOfRegister: () -> Unit
) {
    val authState = remember { mutableStateOf(1) }
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.headlineMedium,
            color = if (authState.value == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null) {
                authState.value = 1
                onClickOfLogin()
            }
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            color = if (authState.value == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null) {
                authState.value = 2
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
    onClickOfFaceBook: () -> Unit
) {

    var loadingState by remember { mutableStateOf(false) }

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
                    text = "Register",
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

@Preview
@Composable
fun PreviewAuthBottom() {
    RegisterBottomComponent(
        onClickOfRegisterButton = {  },
        onClickOfGoogle = {  }
    ) {

    }
}