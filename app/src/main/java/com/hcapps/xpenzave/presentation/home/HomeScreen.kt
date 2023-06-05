package com.hcapps.xpenzave.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.home.component.BudgetProgress

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        MonthBudgetCard(
            onClickOfCalendar = {
                Toast.makeText(context, "Changing Month", Toast.LENGTH_SHORT).show()
            },
            onClickOfEditBudget = {
                Toast.makeText(context, "Editing Budget", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun MonthBudgetCard(
    onClickOfEditBudget: () -> Unit,
    onClickOfCalendar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                shape = Shapes().small
            ) {
                Text(text = "Edit Budget")
            }
        }
    }
}

@Composable
fun BudgetCardHeader(onClickOfCalendar: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "September",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "2019",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
        }
        IconButton(onClick = onClickOfCalendar) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = "Calendar Month",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMonthBudgetCard() {
    MonthBudgetCard({}, {})
}