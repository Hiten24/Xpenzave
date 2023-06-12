package com.hcapps.xpenzave.presentation.expense_log

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.presentation.core.component.ExpenseLog
import com.hcapps.xpenzave.presentation.core.component.MonthHeader
import com.hcapps.xpenzave.presentation.home.component.ExpenseDateHeaderStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpenseLogSection(
    navigateToFiler: () -> Unit,
    navigateToDetails: () -> Unit,
    viewModel: ExpenseLogViewModel = hiltViewModel()
) {

    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        MonthHeader(
            modifier = Modifier
                .fillMaxWidth(),
            date = state.date,
            icon = Icons.Outlined.FilterList,
            onClickOfIcon = navigateToFiler
        )

        ExpenseLog(
            onClickOfDateHeader = { /*TODO*/ },
            onClickOfExpenseItem = navigateToDetails,
            expenses = state.expenses,
            headerStyle = ExpenseDateHeaderStyle
                .defaultExpenseDateHeaderStyle()
                .copy(headerBackgroundColor = MaterialTheme.colorScheme.surface)
        )
    }
}