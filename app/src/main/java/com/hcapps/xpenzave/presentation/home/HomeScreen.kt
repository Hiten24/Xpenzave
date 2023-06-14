package com.hcapps.xpenzave.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.presentation.core.component.calendar.MonthDialog
import com.hcapps.xpenzave.presentation.core.component.calendar.rememberMonthState
import com.hcapps.xpenzave.presentation.home.component.BudgetProgressCard
import com.hcapps.xpenzave.presentation.home.component.RecentExpenseSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    editBudget: (date: String, budgetId: String?) -> Unit,
    expenseLog: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val monthSelectorState by rememberMonthState()

    Column(modifier = Modifier.fillMaxSize()) {
        BudgetProgressCard(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .padding(16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            loading = state.budgetLoading,
            date = state.date,
            onClickOfCalendar = { monthSelectorState.show() },
            onClickOfEditBudget = {
                editBudget(state.date.toString(), state.budgetId)
            },
            progress = state.budgetPercentage,
            budgetAmount = state.budgetAmount,
            totalSpending = state.totalSpending
        )

        Spacer(modifier = Modifier.height(16.dp))

        RecentExpenseSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding()),
            onClickOfSeeAll = expenseLog,
            onClickOfExpenseItem = {},
            onClickOfAddExpense = {},
            expenseLazyState = lazyListState,
            listOfExpense = state.recentExpenses
        )

    }

    if (monthSelectorState.opened()) {
        MonthDialog(
            selectedMonth = state.date.monthValue,
            selectedYear = state.date.year,
            onDismiss = { monthSelectorState.dismiss() },
            onSelectMonthYear = { date ->
                viewModel.onDateChange(date)
                monthSelectorState.dismiss()
            }
        )
    }

}