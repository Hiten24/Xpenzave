package com.hcapps.xpenzave.presentation.core.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun XpenzaveTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    supportingText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions()
) {

    var visualTransformation: MutableState<Boolean>? = null
    if (keyboardType == KeyboardType.Password) {
        visualTransformation = remember { mutableStateOf(true) }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        trailingIcon = {
            val text = when (visualTransformation?.value) {
                true -> { "show" }
                false -> { "hide" }
                else -> null
            }
            text?.let {
                TextButton(onClick = { visualTransformation?.value = !visualTransformation?.value!! }) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        visualTransformation = if (visualTransformation?.value == true) PasswordVisualTransformation() else VisualTransformation.None,
        isError = error.isNullOrEmpty().not(),
        supportingText = {
            if (supportingText != null && error == null) {
                Text(text = supportingText)
            }
            error?.let { Text(text = it) }
        },
        colors = TextFieldDefaults.colors(unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface, unfocusedContainerColor = MaterialTheme.colorScheme.background, focusedContainerColor = MaterialTheme.colorScheme.background),
        singleLine = true,
        keyboardActions = keyboardActions
    )
}