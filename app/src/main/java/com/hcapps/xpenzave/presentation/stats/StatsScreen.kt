package com.hcapps.xpenzave.presentation.stats

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.presentation.core.component.XpenzaveTabRow
import com.hcapps.xpenzave.presentation.core.component.calendar.MonthDialog
import com.hcapps.xpenzave.presentation.expense_detail.ExpenseDetailNavArgs
import com.hcapps.xpenzave.presentation.expense_log.ExpenseLogSection
import com.hcapps.xpenzave.presentation.general_stats.GeneralSection

private val statsSection = listOf("Expense Log", "General")
const val TAB_EXPENSE_LOG = 0
const val TAB_GENERAL = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    paddingValues: PaddingValues,
    navigateToCompare: () -> Unit,
    navigateToFilter: () -> Unit,
    navigateToDetails: (details: ExpenseDetailNavArgs) -> Unit,
    viewModel: StatsViewModel = hiltViewModel()
) {

    val state by viewModel.state
    var dateDialogOpened by remember { mutableStateOf(false) }

    val lazyState = rememberLazyListState()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState(),
        flingAnimationSpec = decayAnimationSpec
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpenseLogTopBar(
                onClickOfCompare = navigateToCompare,
                onClickOfCalender = { dateDialogOpened = true },
                containerColor = MaterialTheme.colorScheme.background,
                scrollBehavior = scrollBehavior
            )
        }
    ) { topBarPadding ->

        when {
            state.loading -> {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .padding(topBarPadding)
                        .height(2.dp)
                )
            }
            viewModel.expenses.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No expenses logged.", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Start building your financial history today.", style = MaterialTheme.typography.labelLarge)
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(topBarPadding)
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    XpenzaveTabRow(
                        modifier = Modifier
                            .shadow(elevation = 2.dp, shape = MaterialTheme.shapes.small),
                        items = statsSection,
                        selectedIndex = state.tabScreen,
                        onSelectionChange = { viewModel.changeScreen(it) }
                    )

                    when (state.tabScreen) {
                        TAB_GENERAL -> GeneralSection()
                        TAB_EXPENSE_LOG -> {
                            ExpenseLogSection(
                                navigateToFiler = navigateToFilter,
                                navigateToDetails = { navigateToDetails(it.toExpenseDetailsArgs()) },
                                date = state.date,
                                expenses = viewModel.expenses.groupBy { it.date },
                                expenseLogLazyState = lazyState
                            )
                        }
                    }

                }
            }
        }
    }

    if (dateDialogOpened) {
        MonthDialog(
            selectedMonth = state.date.monthValue,
            selectedYear = state.date.year,
            onDismiss = { dateDialogOpened = false },
            onSelectMonthYear = {
                viewModel.dateChange(it)
                dateDialogOpened = false
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseLogTopBar(
    onClickOfCompare: () -> Unit,
    onClickOfCalender: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    scrollBehavior: TopAppBarScrollBehavior
) {

    TopAppBar(
        title = {
            Text(text = "Stats")
        },
        actions = {
            IconButton(onClick = onClickOfCompare) {
                Icon(
                    imageVector = Icons.Outlined.Scale,
                    contentDescription = "Calender of Month",
                )
            }
            IconButton(onClick = onClickOfCalender) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "Calender of Month",
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = containerColor),
        scrollBehavior = scrollBehavior
    )
}