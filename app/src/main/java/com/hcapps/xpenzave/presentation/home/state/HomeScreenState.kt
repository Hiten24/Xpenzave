package com.hcapps.xpenzave.presentation.home.state

import androidx.annotation.Keep
import com.hcapps.xpenzave.domain.model.expense.getDummyExpenseByDate
import com.hcapps.xpenzave.util.ExpenseLogType
import java.time.LocalDate

@Keep
data class HomeScreenState(
    val budgetId: String? = null,
    val date: LocalDate = LocalDate.now(),
    val budgetAmount: Double? = null,
    val totalSpending: Double = 0.0,
    val budgetPercentage: Int = 0,
    val recentExpenses: ExpenseLogType? = null,
    val budgetLoading: Boolean = false,
    val recentExpenseLoading: Boolean = false,
) {
    fun calculateBudgetPercentage() = try {
        if (budgetAmount != null && budgetAmount != 0.0) {
            ((totalSpending / budgetAmount) * 100).toInt()
//            if (percentage > 100) 100 else percentage
        } else 0
    } catch (e: Exception) { 0 }
}

fun fakeHomeScreenState() = HomeScreenState(
    date = LocalDate.now(),
    budgetAmount = 2000.0,
    totalSpending = 1200.0,
    recentExpenses = mapOf(getDummyExpenseByDate(
        LocalDate.of(2023, 5, 1)
    ).entries.last().toPair())
)
