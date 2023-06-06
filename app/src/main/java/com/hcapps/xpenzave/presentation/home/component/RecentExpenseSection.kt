package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.home.state.Expense
import com.hcapps.xpenzave.presentation.home.state.ExpensesOfTheDay

@Composable
fun RecentExpenseSection(
    modifier: Modifier = Modifier,
    spaceBetweenItem: Dp = 12.dp,
    onClickOfSeeAll: () -> Unit,
    onClickOfDateHeader: () -> Unit,
    onClickOfExpenseItem: () -> Unit,
    onClickOfAddExpense: () -> Unit,
    expensesOfMonth: List<ExpensesOfTheDay> = emptyList()
) {
    Column(
        modifier = modifier
    ) {

        if (expensesOfMonth.isNotEmpty()) {
            RecentExpenseHeader(modifier = Modifier.fillMaxWidth(), onClickOfSeeAll)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (expensesOfMonth.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(expensesOfMonth) { expenseOfTheDay: ExpensesOfTheDay ->
                    DateHeaderItem(
                        dateOfMonth = expenseOfTheDay.dateOfTheMonth,
                        dayOfMonth = expenseOfTheDay.dayOfTheWeek,
                        onClickOfDateHeader = onClickOfDateHeader
                    )

                    Spacer(modifier = Modifier.height(spaceBetweenItem))

                    expenseOfTheDay.expenses.forEach { expense: Expense ->
                        ExpenseItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onClickOfExpenseItem() }
                                .padding(start = 8.dp),
                            expense = expense
                        )

                        Spacer(modifier = Modifier.height(spaceBetweenItem))

                    }
                }

            }
        } else {
            AddExpenseButton(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                onClickOfAddExpense = onClickOfAddExpense
            )
        }

    }
}

@Composable
fun RecentExpenseHeader(
    modifier: Modifier = Modifier,
    onClickOfSeeAll: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.recent_expense),
            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
        )
        Text(
            text = stringResource(R.string.see_all),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .clip(Shapes().small)
                .clickable { onClickOfSeeAll() }
                .padding(4.dp)
        )
    }
}

@Composable
fun DateHeaderItem(
    dateOfMonth: String,
    dayOfMonth: String,
    style: ExpenseDateHeaderStyle = ExpenseDateHeaderStyle.defaultExpenseDateHeaderStyle(),
    onClickOfDateHeader: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = style.headerBackgroundColor, shape = style.cardShape)
            .border(
                width = style.borderWidth,
                color = style.headerBorderColor,
                shape = style.cardShape
            )
            .clip(Shapes().small)
            .clickable { onClickOfDateHeader() }
            .padding(10.dp)
    ) {
        Text(
            text = dateOfMonth,
            color = style.dateTextColor,
            fontWeight = style.dateFontWeight
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = dayOfMonth, color = style.dayTextColor)
    }
}

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    style: ExpenseItemStyle = ExpenseItemStyle.defaultExpenseItemStyle(),
    expense: Expense
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = expense.icon,
            contentDescription = "Icon",
            tint = style.iconColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            shape = Shapes().small,
            colors = CardDefaults.cardColors(containerColor = style.cardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = style.elevation)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = expense.title)
                Text(
                    text = "${expense.value} ${expense.symbol}",
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    color = style.costTextColor
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecentExpenseSection() {
    RecentExpenseSection(
        onClickOfSeeAll = {},
        onClickOfDateHeader = {},
        onClickOfExpenseItem = {},
        onClickOfAddExpense = {}
    )
}

@Preview
@Composable
fun PreviewRecentExpenseHeader() {
    RecentExpenseHeader(onClickOfSeeAll = { })
}

@Preview
@Composable
fun PreviewDateHeader() {
    DateHeaderItem(dateOfMonth = "1st", dayOfMonth = "Sunday") {}
}

@Preview
@Composable
fun PreviewExpenseItem() {
    ExpenseItem(
        expense =  Expense (icon = Icons.Outlined.Receipt,
        title = "Bills",
        value = 500,
        symbol = "$")
    )
}