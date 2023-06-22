package com.hcapps.xpenzave.presentation.change_password

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.AuthTopBar
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.PasswordObserver
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.NewPasswordChanged
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.OldPasswordChanged
import com.hcapps.xpenzave.presentation.change_password.ChangePasswordEvent.OnPasswordChanged
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.presentation.core.component.input.XpenzaveTextField

@Composable
fun ChangePasswordScreen(
    navigateUp: () -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {

    val state by viewModel.state

    Scaffold(
        topBar = {
            AuthTopBar(
                title = stringResource(id = R.string.change_password_title),
                onNavigation = navigateUp
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(22.dp)) {
            XpenzaveTextField(
                value = state.oldPassword,
                onValueChange = { viewModel.onEvent(OldPasswordChanged(it)) },
                label = stringResource(id = R.string.old_password),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                error = state.oldPasswordError
            )

            XpenzaveTextField(
                value = state.newPassword,
                onValueChange = { viewModel.onEvent(NewPasswordChanged(it)) },
                label = stringResource(id = R.string.new_password),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                error = state.newPasswordError
            )

            AnimatedVisibility(visible = state.passwordState != null) {
                PasswordObserver(passwordState = state.passwordState!!)
            }

            Spacer(modifier = Modifier.height(12.dp))

            XpenzaveButton(
                title = "Change Password",
                enabled = state.oldPassword.isNotEmpty() && state.newPassword.isNotEmpty(),
                loading = state.loading,
                onClickOfButton = {
                    viewModel.onEvent(OnPasswordChanged(navigateUp))
                }
            )
        }
    }
}