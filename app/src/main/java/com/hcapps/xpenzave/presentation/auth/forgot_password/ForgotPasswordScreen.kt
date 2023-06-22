package com.hcapps.xpenzave.presentation.auth.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.AuthTopBar
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.presentation.core.component.input.XpenzaveTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val state by viewModel.state
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            AuthTopBar(
                title = stringResource(id = R.string.forgot_password_title),
                onNavigation = navigateUp
            )
        },
    ) { paddingValues ->  
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(22.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.forgot_password_subtitle),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            XpenzaveTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(ForgotPasswordEvent.EmailChange(it)) },
                label = stringResource(id = R.string.e_mail),
                error = state.emailError,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(24.dp))

            XpenzaveButton(
                title = "Continue",
                enabled = state.email.isNotEmpty(),
                loading = state.loading
            ) {
                viewModel.onEvent(ForgotPasswordEvent.ContinueButton {
                    scope.launch {
                        sheetState.show()
                    }
                })
            }
        }
    }

    if (sheetState.isVisible) {
        ForgotPasswordBottomSheetContent(
            state = sheetState,
            onDismiss = {
                scope.launch { sheetState.hide() }
                navigateUp()
            },
            done = navigateUp
        )
    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordBottomSheetContent(
    state: SheetState,
    onDismiss: () -> Unit,
    done: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = state) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Check your email", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "We have sent a instruction to recover your password to your email",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            XpenzaveButton(title = "Done", onClickOfButton = done)
        }
    }
}