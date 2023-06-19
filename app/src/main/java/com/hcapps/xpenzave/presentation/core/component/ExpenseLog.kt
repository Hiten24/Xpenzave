package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notes
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
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import com.hcapps.xpenzave.presentation.defaultDisplayName
import com.hcapps.xpenzave.presentation.home.component.ExpenseDateHeaderStyle
import com.hcapps.xpenzave.presentation.home.component.ExpenseItemStyle
import com.hcapps.xpenzave.presentation.home.component.RecentExpenseSection
import com.hcapps.xpenzave.presentation.ordinalDayOfMonth
import java.time.LocalDate

typealias ExpenseLogs = Map<LocalDate, List<ExpenseDomainData>>

private const val headerOfTheExpense = 1000
private const val itemOfTheCompose = 1001

@Composable
fun ExpenseLog(
    modifier: Modifier = Modifier,
    onClickOfExpenseItem: (details: ExpenseDomainData) -> Unit,
    lazyState: LazyListState = rememberLazyListState(),
    headerStyle: ExpenseDateHeaderStyle = ExpenseDateHeaderStyle.defaultExpenseDateHeaderStyle(),
    itemStyle: ExpenseItemStyle = ExpenseItemStyle.defaultExpenseItemStyle(),
    expenses: ExpenseLogs = emptyMap()
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
    ) {

        expenses.forEach { (date, listOfExpense) ->
            item(contentType = headerOfTheExpense) {
                DateHeaderItem(
                    date = date,
                    onClickOfDateHeader = {},
                    style = headerStyle
                )
            }

            items(items = listOfExpense) { expense ->
                ExpenseItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickOfExpenseItem(expense) }
                        .padding(start = 8.dp),
                    expense = expense,
                    style = itemStyle
                )
            }

        }
    }
}

@Composable
fun DateHeaderItem(
    date: LocalDate,
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
            text = ordinalDayOfMonth(date.dayOfMonth),
            color = style.dateTextColor,
            fontWeight = style.dateFontWeight
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = date.dayOfWeek.defaultDisplayName(),
            color = style.dayTextColor
        )
    }
}

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    style: ExpenseItemStyle = ExpenseItemStyle.defaultExpenseItemStyle(),
    expense: ExpenseDomainData
) {
    val category = Category.dummies().find { it.id == expense.category }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = category?.icon ?: Icons.Outlined.Notes,
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
                Text(text = category?.name ?: "")
                Text(
                    text = "${expense.amount} $",
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
        onClickOfExpenseItem = {},
        onClickOfAddExpense = {},
        expenseLazyState = rememberLazyListState()
    )
}

@Preview
@Composable
fun PreviewDateHeader() {
    DateHeaderItem(LocalDate.now()) {}
}

@Preview
@Composable
fun PreviewExpenseItem() {
    ExpenseItem(expense = ExpenseDomainData.dummy())
}