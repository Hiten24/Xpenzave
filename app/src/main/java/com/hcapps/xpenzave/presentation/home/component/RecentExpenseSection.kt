package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import com.hcapps.xpenzave.presentation.core.component.ExpenseLog
import com.hcapps.xpenzave.presentation.core.component.ExpenseLogs

@Composable
fun RecentExpenseSection(
    modifier: Modifier = Modifier,
    spaceBetweenItem: Dp = 12.dp,
    onClickOfSeeAll: () -> Unit,
    onClickOfExpenseItem: (details: ExpenseDomainData) -> Unit,
    onClickOfAddExpense: () -> Unit,
    expenseLazyState: LazyListState,
    listOfExpense: ExpenseLogs? = null,
) {
    Column(
        modifier = modifier
    ) {

        if (listOfExpense.isNullOrEmpty().not()) {
            RecentExpenseHeader(modifier = Modifier.fillMaxWidth(), onClickOfSeeAll)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (listOfExpense.isNullOrEmpty().not()) {
            ExpenseLog(
                modifier = Modifier.fillMaxSize(),
                onClickOfExpenseItem = onClickOfExpenseItem,
                lazyState = expenseLazyState,
                expenses = listOfExpense ?: emptyMap()
            )
        } else {
            listOfExpense?.let {
                AddExpenseContainer(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    onClickOfAddExpense = onClickOfAddExpense
                )
            }
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
        LargeButton(onClickOfAddExpense = onClickOfAddExpense)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}