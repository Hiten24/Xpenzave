package com.hcapps.xpenzave.presentation.general_stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneralSection(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        /*BudgetProgressCard(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f),
            onClickOfEditBudget = {},
            onClickOfCalendar = {}
        )

        CategoryExpensesGraph(
            categories = CategoryData.defaultCategoryGraphs()
        )*/

        DayExpenseGraph()

    }

}