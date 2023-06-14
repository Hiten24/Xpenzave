package com.hcapps.xpenzave.presentation.general_stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryBarChartData
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryExpensesGraph
import com.hcapps.xpenzave.presentation.home.component.BudgetProgressCard
import java.time.LocalDate

@Composable
fun GeneralSection(
    modifier: Modifier = Modifier,
    totalSpend: Double,
    budget: Double,
    percentage: Int,
    date: LocalDate,
    categoriesBarGraphData: List<CategoryBarChartData>
) {

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
                .height(320.dp),
            budgetAmount = budget,
            date = date,
            progress = percentage,
            totalSpending = totalSpend
        )

        CategoryExpensesGraph(
            categories = categoriesBarGraphData
        )

//        DayExpenseGraph(data)

    }

}