package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.home.component.ExpenseDateHeaderStyle
import com.hcapps.xpenzave.presentation.home.component.ExpenseItemStyle
import com.hcapps.xpenzave.presentation.home.component.RecentExpenseSection
import com.hcapps.xpenzave.presentation.home.state.Expense
import com.hcapps.xpenzave.presentation.home.state.ExpensesOfTheDay

@Composable
fun ExpenseLog(
    modifier: Modifier = Modifier,
    spaceBetweenItem: Dp = 12.dp,
    onClickOfDateHeader: () -> Unit,
    onClickOfExpenseItem: () -> Unit,
    lazyState: LazyListState = rememberLazyListState(),
    headerStyle: ExpenseDateHeaderStyle = ExpenseDateHeaderStyle.defaultExpenseDateHeaderStyle(),
    itemStyle: ExpenseItemStyle = ExpenseItemStyle.defaultExpenseItemStyle(),
    expensesOfMonth: List<ExpensesOfTheDay> = emptyList()
) {
    LazyColumn(modifier = modifier.fillMaxSize(), state = lazyState) {

        items(expensesOfMonth) { expenseOfTheDay: ExpensesOfTheDay ->
            DateHeaderItem(
                dateOfMonth = expenseOfTheDay.dateOfTheMonth,
                dayOfMonth = expenseOfTheDay.dayOfTheWeek,
                onClickOfDateHeader = onClickOfDateHeader,
                style = headerStyle
            )

            Spacer(modifier = Modifier.height(spaceBetweenItem))

            expenseOfTheDay.expenses.forEach { expense: Expense ->
                ExpenseItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickOfExpenseItem() }
                        .padding(start = 8.dp),
                    expense = expense,
                    style = itemStyle
                )

                Spacer(modifier = Modifier.height(spaceBetweenItem))

            }
        }

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