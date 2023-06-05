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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddExpenseButton(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    onClickOfAddExpense: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = verticalArrangement
    ) {
        FilledIconButton(
            modifier = Modifier.size(68.dp),
            onClick = { onClickOfAddExpense() }
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add Expense button",
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
fun PreviewAddExpenseButton() {
    AddExpenseButton() {}
}