package com.hcapps.xpenzave.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.core.AlertDialogState
import com.hcapps.xpenzave.presentation.core.rememberAlertDialogState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordDialog(
    state: AlertDialogState,
    oldPasswordValue: String,
    newPasswordValue: String,
    oldPasswordError: String? = null,
    newPasswordError: String? = null,
    oldPasswordChange: (String) -> Unit,
    newPasswordChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onPasswordChange: () -> Unit
) {

    if (!state.opened()) return

    var oldPasswordVisualTransformation by remember { mutableStateOf(false) }
    var newPasswordVisualTransformation by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = oldPasswordValue,
                    onValueChange = oldPasswordChange,
                    label = { Text(text = stringResource(R.string.old_password)) } ,
                    modifier = Modifier.fillMaxWidth(),
                    isError = oldPasswordError.isNullOrEmpty().not(),
                    supportingText = { oldPasswordError?.let { Text(text = it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                    visualTransformation = if (oldPasswordVisualTransformation) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (oldPasswordVisualTransformation) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                        IconButton(onClick = { oldPasswordVisualTransformation = !oldPasswordVisualTransformation }) {
                            Icon(imageVector = icon, contentDescription = null)
                        }
                    },
                    maxLines = 1,
                    singleLine = true
                )
                OutlinedTextField(
                    value = newPasswordValue,
                    onValueChange = newPasswordChange,
                    label = { Text(text = stringResource(R.string.new_password)) } ,
                    modifier = Modifier.fillMaxWidth(),
                    isError = newPasswordError.isNullOrEmpty().not(),
                    supportingText = { newPasswordError?.let { Text(text = it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions.Default,
                    visualTransformation = if (newPasswordVisualTransformation) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (newPasswordVisualTransformation) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                        IconButton(onClick = { newPasswordVisualTransformation = !newPasswordVisualTransformation }) {
                            Icon(imageVector = icon, contentDescription = null)
                        }
                    },
                    maxLines = 1,
                    singleLine = true
                )
                Row(modifier = Modifier.align(Alignment.End), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    TextButton(
                        onClick = onPasswordChange,
                        enabled = oldPasswordValue.isNotEmpty() || newPasswordValue.isNotEmpty()
                    ) {
                        Text(text = stringResource(R.string.change))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewChangePasswordDialog() {
    ChangePasswordDialog(
        oldPasswordValue = "Old password",
        newPasswordValue = "New password",
        oldPasswordChange = {},
        newPasswordChange = {},
        onDismissRequest = {},
        onPasswordChange = {},
        state = rememberAlertDialogState()
    )
}