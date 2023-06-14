package com.hcapps.xpenzave.presentation.stats

import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryBarChartData

data class StatisticsState(
    val budget: Double = 0.0,
    val totalSpend: Double = 0.0,
    val budgetPercentage: Int = 0,
    val categoryBarChartData: List<CategoryBarChartData> = emptyList()
)
