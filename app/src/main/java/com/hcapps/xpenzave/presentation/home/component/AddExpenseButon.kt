package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AddExpenseContainer(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    onClickOfAddExpense: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = verticalArrangement
    ) {
        AddExpenseContainer(onClickOfAddExpense = onClickOfAddExpense)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AddExpenseButton(
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