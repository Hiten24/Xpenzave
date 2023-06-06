package com.hcapps.xpenzave.presentation.home.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.DragHandle
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Villa

data class ExpensesOfTheDay(
    val dateOfTheMonth: String,
    val dayOfTheWeek: String,
    val expenses: List<Expense>
)

fun dummyExpensesOfTheDay(): List<ExpensesOfTheDay> {
    return listOf(
        ExpensesOfTheDay(
            "1st",
            "Sunday",
            listOf(
                Expense(Icons.Outlined.Receipt, title = "Bills", 500, "$"),
                Expense(Icons.Outlined.ShoppingBag, title = "Shopping", 200, "$"),
                Expense(Icons.Outlined.Restaurant, title = "Dinner", 800, "$"),
            )
        ),
        ExpensesOfTheDay(
            "2st",
            "Monday",
            listOf(
                Expense(Icons.Outlined.LocalCafe, title = "Breakfast", 200, "$"),
                Expense(Icons.Outlined.Savings, title = "Investment", 1000, "$")
            )
        ),
        ExpensesOfTheDay(
            "3rd",
            "Tuesday",
            listOf(
                Expense(Icons.Outlined.Medication, title = "Medication", 100, "$"),
                Expense(Icons.Outlined.PhoneIphone, title = "Iphone 14 Pro Max", 1200, "$"),
                Expense(Icons.Outlined.DirectionsCar, title = "Transportation", 150, "$"),
                Expense(Icons.Outlined.Villa, title = "Vacation", 1500, "$"),
                Expense(Icons.Outlined.DragHandle, title = "Others", 50, "$"),
            )
        )
    )
}