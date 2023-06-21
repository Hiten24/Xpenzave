package com.hcapps.xpenzave.presentation.on_board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.ui.theme.ButtonHeight

@Composable
fun OnBoardScreen(
    logIn: () -> Unit,
    createAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.ic_on_board_background),
                contentScale = ContentScale.Crop,
                alpha = 0.03f,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            )
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_on_boarding_illustration),
            contentDescription = null,
            alignment = Alignment.Center
        )

        Column(modifier = Modifier.padding(horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.on_board_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                lineHeight = TextUnit(38f, TextUnitType.Sp),
                letterSpacing = TextUnit(1f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.on_board_subtitle),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(22f, TextUnitType.Sp),
                letterSpacing = TextUnit(0.5f, TextUnitType.Sp)
            )
        }

        ButtonSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            createAccount = createAccount,
            logIn = logIn
        )
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier,
    createAccount: () -> Unit,
    logIn: () -> Unit
) {
    Column(modifier = modifier) {
        XpenzaveButton(title = "Create an account", onClickOfButton = createAccount)
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(
            onClick = logIn,
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonHeight),
            shape = MaterialTheme.shapes.small
        ) {
            Text(text = "Log In")
        }
    }
}

@Preview
@Composable
fun PreviewBoard() {
    OnBoardScreen({}, {})
}