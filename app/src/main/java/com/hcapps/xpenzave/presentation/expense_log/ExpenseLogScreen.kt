package com.hcapps.xpenzave.presentation.expense_log

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import com.hcapps.xpenzave.presentation.core.component.ExpenseLog
import com.hcapps.xpenzave.presentation.core.component.ExpenseLogs
import com.hcapps.xpenzave.presentation.core.component.MonthHeader
import com.hcapps.xpenzave.presentation.home.component.ExpenseDateHeaderStyle
import java.time.LocalDate

@Composable
fun ExpenseLogSection(
    navigateToFiler: () -> Unit,
    navigateToDetails: (details: ExpenseDomainData) -> Unit,
    expenseLogLazyState: LazyListState,
    date: LocalDate,
    expenses: ExpenseLogs
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        MonthHeader(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 8.dp),
            date = date,
            icon = Icons.Outlined.FilterList,
            onClickOfIcon = navigateToFiler
        )

        ExpenseLog(
            onClickOfExpenseItem = navigateToDetails,
            expenses = expenses,
            headerStyle = ExpenseDateHeaderStyle
                .defaultExpenseDateHeaderStyle()
                .copy(headerBackgroundColor = MaterialTheme.colorScheme.surface),
            lazyState = expenseLogLazyState
        )
    }
}