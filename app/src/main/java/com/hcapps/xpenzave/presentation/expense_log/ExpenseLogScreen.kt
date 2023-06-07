package com.hcapps.xpenzave.presentation.expense_log

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hcapps.xpenzave.presentation.core.component.ExpenseLog
import com.hcapps.xpenzave.presentation.core.component.MonthHeader
import com.hcapps.xpenzave.presentation.home.component.ExpenseDateHeaderStyle
import com.hcapps.xpenzave.presentation.home.state.dummyExpensesOfTheDay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpenseLogSection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        MonthHeader(
            modifier = Modifier
                .fillMaxWidth(),
            month = "September",
            year = "2019",
            icon = Icons.Outlined.FilterList,
            onClickOfIcon = {

            }
        )

        ExpenseLog(
            onClickOfDateHeader = { /*TODO*/ },
            onClickOfExpenseItem = { /*TODO*/ },
            expensesOfMonth = dummyExpensesOfTheDay(),
            headerStyle = ExpenseDateHeaderStyle
                .defaultExpenseDateHeaderStyle()
                .copy(headerBackgroundColor = MaterialTheme.colorScheme.background)
        )
    }
}