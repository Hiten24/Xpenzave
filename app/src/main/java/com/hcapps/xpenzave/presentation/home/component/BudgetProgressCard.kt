package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
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
import com.hcapps.xpenzave.presentation.core.component.CircularProgress
import com.hcapps.xpenzave.presentation.core.component.MonthHeader
import com.hcapps.xpenzave.ui.theme.DefaultCardElevation
import java.time.LocalDate

@Composable
fun BudgetProgressCard(
    modifier: Modifier = Modifier,
    date: LocalDate = LocalDate.now(),
    progress: Int = 0,
    budgetAmount: Double? = 0.0,
    totalSpending: Double = 0.0,
    loading: Boolean = false,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    cardElevation: Dp = DefaultCardElevation,
    onClickOfEditBudget: (() -> Unit)? = null,
    onClickOfCalendar: (() -> Unit)? = null
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            MonthHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                date = date,
                icon = Icons.Outlined.CalendarMonth,
                onClickOfIcon = onClickOfCalendar
            )

            if (!loading) {
                BudgetProgress(
                    progress = progress,
                    archWidth = 160f,
                    containerWidth = 250.dp,
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
                        append("$totalSpending $symbol")
                    }
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))) {
                        append(" / ${budgetAmount ?: "-"}")
                        budgetAmount?.let { append(" $") }
                    }
                }

                Text(text = budget)

                onClickOfEditBudget?.let {
                    OutlinedButton(
                        onClick = it,
                        shape = Shapes().small,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Text(text = stringResource(R.string.edit_budget))
                    }
                }

            } else {
                CircularProgress()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMonthBudgetCard() {
    BudgetProgressCard(onClickOfEditBudget = {}) {}
}