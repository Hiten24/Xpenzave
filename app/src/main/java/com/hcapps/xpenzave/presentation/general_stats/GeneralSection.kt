package com.hcapps.xpenzave.presentation.general_stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryData
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryExpensesGraph
import com.hcapps.xpenzave.presentation.home.component.BudgetProgressCard

@Composable
fun GeneralSection(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        BudgetProgressCard(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f),
            onClickOfEditBudget = {},
            onClickOfCalendar = {}
        )

        CategoryExpensesGraph(
            categories = CategoryData.defaultCategoryGraphs()
        )

    }

}