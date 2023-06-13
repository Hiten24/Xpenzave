package com.hcapps.xpenzave.presentation.core.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.ui.theme.ButtonHeight

@Composable
fun XpenzaveButton(
    modifier: Modifier = Modifier,
    title: String,
    state: ButtonState = ButtonState(),
    onClickOfButton: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        enabled = state.enabled && state.loading.not(),
        onClick = onClickOfButton,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = Shapes().small
    ) {
        if (state.loading.not()) {
            Text(text = title)
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Preview
@Composable
fun PreviewXpenzaveColor() {
    XpenzaveButton(title = "Save") {}
}