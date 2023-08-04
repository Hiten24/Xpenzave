package com.hcapps.xpenzave.presentation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.AuthTopBar
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.EmailChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.PasswordChanged
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.Register
import com.hcapps.xpenzave.presentation.auth.event.AuthEvent.SetPasswordFocusChanged
import com.hcapps.xpenzave.presentation.auth.event.PasswordState
import com.hcapps.xpenzave.presentation.core.UiEventReceiver
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.presentation.core.component.input.XpenzaveTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit,
    login: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val state by viewModel.state

    viewModel.uiEvent.UiEventReceiver()

    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AuthTopBar(
                title = stringResource(id = R.string.register_title),
                subtitle = stringResource(id = R.string.register_subtitle),
                actionText = stringResource(id = R.string.register_action_text),
                onNavigation = navigateUp,
                onAction = login,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 24.dp)
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            XpenzaveTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(EmailChanged(it)) },
                label = stringResource(id = R.string.e_mail),
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                error = state.emailError
            )

            XpenzaveTextField(
                modifier = Modifier.onFocusChanged {
                    viewModel.onEvent(SetPasswordFocusChanged(it.isFocused))
                },
                value = state.password,
                onValueChange = {
                    if (it.length <= 20) {
                        viewModel.onEvent(PasswordChanged(it))
                    }
                },
                label = stringResource(id = R.string.set_password),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                error = state.passwordError,
                supportingText = state.createPasswordState?.let { getPasswordRulesSupportingText(it) }
            )

            /*XpenzaveTextField(
                value = state.confirmPassword,
                onValueChange = { viewModel.onEvent(ConfirmPasswordChanged(it)) },
                label = stringResource(id = R.string.confirm_password),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                error = state.confirmPasswordError
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            Box() {
                XpenzaveButton(
                    title = "Register",
                    enabled = state.email.isNotEmpty() && state.password.isNotEmpty(),
                    loading = state.loading,
                    onClickOfButton = { viewModel.onEvent(Register(navigateToHome)) }
                )
            }
        }
    }

}

@Composable
private fun getPasswordRulesSupportingText(state: PasswordState): String {
    val checkIcon = stringResource(id = R.string.check_unicode)
    val bulletIcon = stringResource(id = R.string.bullet_point_unicode)
    return stringResource(id = R.string.password_rule_title) + "\n" + "\n" +
    stringResource(id = R.string.password_rule_1, if (state.shouldBeMin8Max20Char) checkIcon else bulletIcon) + "\n" +
    stringResource(id = R.string.password_rule_2, if (state.shouldHaveALowerCase) checkIcon else bulletIcon) + "\n" +
    stringResource(id = R.string.password_rule_3, if (state.shouldHaveAUpperCase) checkIcon else bulletIcon) + "\n" +
    stringResource(id = R.string.password_rule_4, if (state.shouldHaveANumberOrAcceptableCharacter) checkIcon else bulletIcon)
}