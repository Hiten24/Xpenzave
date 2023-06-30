package com.hcapps.xpenzave.presentation.auth.forgot_password_activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.AuthTopBar
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.ResetPasswordEvent.ConfirmPasswordChanged
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.ResetPasswordEvent.IntentData
import com.hcapps.xpenzave.presentation.auth.forgot_password_activity.ResetPasswordEvent.PasswordChanged
import com.hcapps.xpenzave.presentation.core.UiEventReceiver
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.presentation.core.component.input.XpenzaveTextField
import com.hcapps.xpenzave.ui.theme.XpenzaveTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ResetPasswordActivity :  ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XpenzaveTheme {
                ResetPasswordContent(link = intent.data, finish = { finish() })
            }
        }
    }

}

@Composable
fun ResetPasswordContent(
    viewModel: RestPasswordViewModel = hiltViewModel(),
    link: Uri?,
    finish: () -> Unit
) {

    val context = LocalContext.current
    val isExpired = false

    viewModel.uiEvent.UiEventReceiver()

    link?.let {
        val userId = it.getQueryParameter("userId")
        val secret = it.getQueryParameter("secret")
        val expire = it.getQueryParameter("expire")?.replace(" ", "T")
//        val expireDateTime = LocalDateTime.parse(expire)
//        isExpired = LocalDateTime.now().compareTo(expireDateTime) == 1
        Timber.i("userId: $userId")
        Timber.i("secret: $secret")
        Timber.i("expire: $expire")
        viewModel.onEvent(IntentData(userId, secret, expire))
    }

    val state by viewModel.state
    Scaffold(
        topBar = {
            AuthTopBar(
                title = stringResource(id = R.string.reset_password_title),
                onNavigation = finish,
                onAction = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isExpired) {
                Image(
                    painter = painterResource(id = R.drawable.ic_reset_password_link_expired),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.reset_password_expired),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = stringResource(id = R.string.reset_password_expired_subtitle),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
            } else {
                XpenzaveTextField(
                    value = state.password,
                    onValueChange = { viewModel.onEvent(PasswordChanged(it)) },
                    label = stringResource(id = R.string.set_password),
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    error = state.passwordError
                )

                AnimatedVisibility(visible = state.passwordState != null) {
                    PasswordObserver(passwordState = state.passwordState!!)
                }

                XpenzaveTextField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onEvent(ConfirmPasswordChanged(it)) },
                    label = stringResource(id = R.string.confirm_password),
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    error = state.confirmPasswordError
                )

                Spacer(modifier = Modifier.height(12.dp))

                XpenzaveButton(
                    title = "Register",
                    enabled = state.password.isNotEmpty() && state.confirmPassword.isNotEmpty(),
                    loading = state.loading,
                    onClickOfButton = {
                        viewModel.onEvent(ResetPasswordEvent.OnPasswordChanged(
                            onSuccess = finish,
                            onError = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                        ))
                    }
                )
            }
        }
    }
}