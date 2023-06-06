package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hcapps.xpenzave.ui.theme.ButtonHeight

@Composable
fun XpenzaveButton(
    modifier: Modifier = Modifier,
    title: String,
    onClickOfButton: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth().height(ButtonHeight),
        onClick = onClickOfButton,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = Shapes().small
    ) {
        Text(text = title)
    }
}

@Preview
@Composable
fun PreviewXpenzaveColor() {
    XpenzaveButton(title = "Save") {}
}