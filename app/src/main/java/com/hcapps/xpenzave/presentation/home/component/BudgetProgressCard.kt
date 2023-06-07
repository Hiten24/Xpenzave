package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.home.BudgetCardHeader

@Composable
fun BudgetProgressCard(
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    cardElevation: Dp = 4.dp,
    onClickOfEditBudget: () -> Unit,
    onClickOfCalendar: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BudgetCardHeader(onClickOfCalendar)

            BudgetProgress(
                archWidth = 130f,
                containerWidth = 200.dp,
                progress = 69,
                progressContainerColor = MaterialTheme.colorScheme.outlineVariant,
                progressGradient = listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                )
            )

            val budget = buildAnnotatedString {
                val symbol = "$"
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("1000 $symbol")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))) {
                    append(" / 2000 $")
                }
            }

            Text(text = budget)

            OutlinedButton(
                onClick = onClickOfEditBudget,
                shape = Shapes().small,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Text(text = stringResource(R.string.edit_budget))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMonthBudgetCard() {
    BudgetProgressCard(onClickOfEditBudget = {}, onClickOfCalendar =  {})
}