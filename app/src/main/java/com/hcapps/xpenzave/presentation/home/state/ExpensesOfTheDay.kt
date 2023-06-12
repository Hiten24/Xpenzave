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
                Expense(icon = Icons.Outlined.Receipt, title = "Bills", value = 500, symbol = "$"),
                Expense(icon = Icons.Outlined.ShoppingBag, title = "Shopping", value = 200, symbol = "$"),
                Expense(icon = Icons.Outlined.Restaurant, title = "Dinner", value = 800, symbol = "$"),
            )
        ),
        ExpensesOfTheDay(
            "2st",
            "Monday",
            listOf(
                Expense(icon = Icons.Outlined.LocalCafe, title = "Breakfast", value = 200, symbol = "$"),
                Expense(icon = Icons.Outlined.Savings, title = "Investment", value = 1000, symbol = "$"),
            )
        ),
        ExpensesOfTheDay(
            "3rd",
            "Tuesday",
            listOf(
                Expense(icon = Icons.Outlined.Medication, title = "Medication", value = 100, symbol = "$"),
                Expense(icon = Icons.Outlined.PhoneIphone, title = "Iphone 14 Pro Max", value = 1200, symbol = "$"),
                Expense(icon = Icons.Outlined.DirectionsCar, title = "Transportation", value = 150, symbol = "$"),
                Expense(icon = Icons.Outlined.Villa, title = "Vacation", value = 1500, symbol = "$"),
                Expense(icon = Icons.Outlined.DragHandle, title = "Others", value = 50, symbol = "$"),
            )
        )
    )
}