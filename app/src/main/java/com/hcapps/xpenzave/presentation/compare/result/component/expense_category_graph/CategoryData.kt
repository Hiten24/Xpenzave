package com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import com.hcapps.xpenzave.domain.model.category.Category

data class CategoryBarChartData(
    val category: Category,
    val budget: Double,
    val totalSpendByCategory: Double,
    val budgetPercentageByCategory: Int
) {
    companion object {

        fun dummyCategory() = CategoryBarChartData(category = Category(id = "",name ="Total", icon = Icons.Outlined.Home, true), budget = 1000.0, totalSpendByCategory = 1000.0, budgetPercentageByCategory = 100)

        fun defaultCategoryGraphs() = listOf(
            CategoryBarChartData(category = Category(id = "",name ="Total", icon = Icons.Outlined.Home, true), budget = 1000.0, totalSpendByCategory = 1000.0, budgetPercentageByCategory = 100),
            CategoryBarChartData(category = Category(id = "",name ="Bills", icon = Icons.Outlined.Home, false), budget = 1000.0, totalSpendByCategory = 1250.0, budgetPercentageByCategory = 80),
            CategoryBarChartData(category = Category(id = "",name ="Food", icon = Icons.Outlined.Home, false), budget = 1000.0, totalSpendByCategory = 1667.0, budgetPercentageByCategory = 60),
            CategoryBarChartData(category = Category(id = "",name ="Clothes", icon = Icons.Outlined.Home, false), budget = 1000.0, totalSpendByCategory = 1429.0, budgetPercentageByCategory = 70),
            CategoryBarChartData(category = Category(id = "",name ="Transport", icon = Icons.Outlined.Home, false), budget = 1000.0, totalSpendByCategory = 1429.0, budgetPercentageByCategory = 70),
            CategoryBarChartData(category = Category(id = "",name ="Fun", icon = Icons.Outlined.Home, false), budget = 1000.0, totalSpendByCategory = 1667.0, budgetPercentageByCategory = 60),
            CategoryBarChartData(category = Category(id = "",name ="Other", icon = Icons.Outlined.Home, false), budget = 1000.0, totalSpendByCategory = 1250.0, budgetPercentageByCategory = 80),
        )

        fun defaultCompareCategoryGraphs() = emptyList<CategoryBarChartData>()

        /*fun defaultCompareCategoryGraphs() = listOf(
            CategoryBarChartData(categoryName ="Total", expense = listOf(ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendingOfMonth = 1000, percentage = 100), ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendByCategory = 1000, budgetPercentageByCategory = 80),
            CategoryBarChartData(categoryName ="Bills", expense = listOf(ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 40), ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendByCategory = 1250, budgetPercentageByCategory = 60),
            CategoryBarChartData(categoryName ="Food", expense = listOf(ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 50), ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendByCategory = 1667, budgetPercentageByCategory = 20),
            CategoryBarChartData(categoryName ="Clothes", expense = listOf(ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 40), ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendByCategory = 1429, budgetPercentageByCategory = 30),
            CategoryBarChartData(categoryName ="Transport", expense = listOf(ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 30), ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendByCategory = 1429, budgetPercentageByCategory = 10),
            CategoryBarChartData(categoryName ="Fun", expense = listOf(ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 60), ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendByCategory = 1667, budgetPercentageByCategory = 20),
            CategoryBarChartData(categoryName ="Other", expense = listOf(ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 10), ExpenseDataForGraph(budgetOfMonth = 1000, totalSpendByCategory = 1250, budgetPercentageByCategory = 30),
        )*/

    }
}

data class ExpenseDataForGraph(
    val budgetOfMonth: Int,
    val totalSpendingOfMonth: Int,
    val percentage: Int
) {
    companion object {
        fun emptyExpense() = ExpenseDataForGraph(
            budgetOfMonth = 0, totalSpendingOfMonth = 0, percentage = 0
        )
    }
}