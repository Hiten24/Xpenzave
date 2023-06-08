package com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph

data class CategoryData(
    val categoryName: String,
    val expense: List<ExpenseData>
) {
    companion object {

        fun dummyCategory() = CategoryData(categoryName ="Total", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1000, percentage = 100)))

        fun defaultCategoryGraphs() = listOf(
            CategoryData(categoryName ="Total", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1000, percentage = 100))),
            CategoryData(categoryName ="Bills", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 80))),
            CategoryData(categoryName ="Food", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 60))),
            CategoryData(categoryName ="Clothes", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 70))),
            CategoryData(categoryName ="Transport", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 70))),
            CategoryData(categoryName ="Fun", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 60))),
            CategoryData(categoryName ="Other", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 80))),
        )

        fun defaultCompareCategoryGraphs() = listOf(
            CategoryData(categoryName ="Total", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1000, percentage = 100), ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1000, percentage = 80))),
            CategoryData(categoryName ="Bills", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 40), ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 60))),
            CategoryData(categoryName ="Food", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 50), ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 20))),
            CategoryData(categoryName ="Clothes", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 40), ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 30))),
            CategoryData(categoryName ="Transport", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 30), ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1429, percentage = 10))),
            CategoryData(categoryName ="Fun", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 60), ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1667, percentage = 20))),
            CategoryData(categoryName ="Other", expense = listOf(ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 10), ExpenseData(budgetOfMonth = 1000, totalSpendingOfMonth = 1250, percentage = 30))),
        )

    }
}

data class ExpenseData(
    val budgetOfMonth: Int,
    val totalSpendingOfMonth: Int,
    val percentage: Int
) {
    companion object {
        fun emptyExpense() = ExpenseData(
            budgetOfMonth = 0, totalSpendingOfMonth = 0, percentage = 0
        )
    }
}