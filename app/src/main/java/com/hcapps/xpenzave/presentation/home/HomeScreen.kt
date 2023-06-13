package com.hcapps.xpenzave.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.home.component.BudgetProgressCard
import com.hcapps.xpenzave.presentation.home.component.RecentExpenseSection
import com.hcapps.xpenzave.presentation.home.state.dummyExpensesOfTheDay
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navigateToEditBudget: (String) -> Unit
) {

    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val date = LocalDate.now()

    Column(modifier = Modifier.fillMaxSize()) {
        BudgetProgressCard(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .padding(16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            onClickOfCalendar = { Toast.makeText(context, "Changing Month", Toast.LENGTH_SHORT).show() },
            onClickOfEditBudget = { navigateToEditBudget("${date.monthValue}-${date.year}") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RecentExpenseSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding()),
            onClickOfSeeAll = {},
            onClickOfExpenseItem = {},
            expensesOfMonth = dummyExpensesOfTheDay(),
            onClickOfAddExpense = {},
            expenseLazyState = lazyListState
        )

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