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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.ui.theme.XpenzaveColor

@Composable
fun RegisterScreen() {
    val context = LocalContext.current
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

        RegisterMiddleComponent()

        RegisterBottomComponent(
            onClickOfRegisterButton = {
                Toast.makeText(context, "Registering", Toast.LENGTH_SHORT).show()
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
            color = if (authState.value == 1) XpenzaveColor.colorSchema.primary else XpenzaveColor.colorSchema.primary.copy(alpha = 0.5f),
            modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null) {
                authState.value = 1
            }
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            color = if (authState.value == 2) XpenzaveColor.colorSchema.primary else XpenzaveColor.colorSchema.primary.copy(alpha = 0.5f),
            modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null) {
                authState.value = 2
            }
        )
    }
}

@Composable
fun RegisterMiddleComponent() {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
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
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onClickOfRegisterButton() },
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes().small,
            colors = ButtonDefaults.buttonColors(containerColor = XpenzaveColor.colorSchema.primary)
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "or continue with", color = MaterialTheme.colorScheme.onSurface)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { onClickOfFaceBook() },
                shape = Shapes().small,
                colors = ButtonDefaults
                    .buttonColors(containerColor = XpenzaveColor.colorSchema.primary.copy(alpha = 0.1f)),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "FaceBook",
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { onClickOfGoogle() },
                shape = Shapes().small,
                colors = ButtonDefaults
                    .buttonColors(containerColor = XpenzaveColor.colorSchema.primary.copy(alpha = 0.1f)),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Google",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}