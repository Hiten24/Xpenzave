package com.hcapps.xpenzave.presentation.auth.forgot_password_activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.auth.event.PasswordState

@Composable
fun PasswordObserver(passwordState: PasswordState) {
    passwordState.let {
        val checkIcon = stringResource(id = R.string.check_unicode)
        val bulletIcon = stringResource(id = R.string.bullet_point_unicode)
        Column {
            Text(
                text = stringResource(id = R.string.password_rule_title),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_1,
                    if (it.shouldBeMin8Max20Char) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_2,
                    if (it.shouldHaveALowerCase) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_3,
                    if (it.shouldHaveAUpperCase) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = R.string.password_rule_4,
                    if (it.shouldHaveANumberOrAcceptableCharacter) checkIcon else bulletIcon
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}