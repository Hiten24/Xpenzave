package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LargeButton(
    modifier: Modifier = Modifier,
    buttonSize: Dp = 68.dp,
    iconSize: Dp = 32.dp,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    onClickOfAddExpense: () -> Unit
) {
    FilledIconButton(
        modifier = modifier.size(buttonSize),
        onClick = { onClickOfAddExpense() },
        colors = IconButtonDefaults.filledIconButtonColors(containerColor = buttonColor)
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add Expense button",
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

@Preview
@Composable
fun PreviewAddExpenseButton() {
    AddExpenseContainer() {}
}