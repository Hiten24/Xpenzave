package com.hcapps.xpenzave.presentation.stats

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.presentation.core.component.XpenzaveTabRow
import com.hcapps.xpenzave.presentation.expense_log.ExpenseLogSection
import com.hcapps.xpenzave.presentation.general_stats.GeneralSection

private val statsSection = listOf("General", "Expense Log")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateScreen(
    paddingValues: PaddingValues,
    navigateToCompare: () -> Unit,
    navigateToCalendar: () -> Unit,
    navigateToFilter: () -> Unit,
    navigateToDetails: () -> Unit,
    viewModel: StateViewModel = hiltViewModel()
) {

    var tabState by viewModel.tabState
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
                onClickOfCalender = navigateToCalendar,
                containerColor = MaterialTheme.colorScheme.background,
                scrollBehavior = scrollBehavior
            )
        }
    ) { topBarPadding ->
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
                selectedIndex = tabState,
                onSelectionChange = { tabState = it }
            )

            if (tabState == 0) {
                GeneralSection()
            } else {
                ExpenseLogSection(
                    navigateToFiler = navigateToFilter,
                    navigateToDetails = navigateToDetails,
                    expenseLogLazyState = lazyState
                )
            }

        }
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