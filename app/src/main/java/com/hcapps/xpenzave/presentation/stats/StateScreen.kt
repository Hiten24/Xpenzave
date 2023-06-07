package com.hcapps.xpenzave.presentation.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.core.component.XpenzaveTabRow
import com.hcapps.xpenzave.presentation.expense_log.ExpenseLogSection
import com.hcapps.xpenzave.presentation.general_stats.GeneralSection

private val statsSection = listOf("General", "Expense Log")

@Composable
fun StateScreen() {

    var tabState by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            ExpenseLogTopBar(
                onClickOfCompare = {},
                onClickOfCalender = {}
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            XpenzaveTabRow(
                modifier = Modifier
                    .padding(paddingValue)
                    .shadow(elevation = 2.dp, shape = MaterialTheme.shapes.small),
                items = statsSection,
                selectedIndex = tabState,
                onSelectionChange = { tabState = it }
            )

            Spacer(modifier = Modifier.height(22.dp))

            if (tabState == 0) {
                GeneralSection()
            } else {
                ExpenseLogSection()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseLogTopBar(
    onClickOfCompare: () -> Unit,
    onClickOfCalender: () -> Unit
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
//                    tint = MaterialTheme.colorScheme.primary.copy(0.6f)
                )
            }
            IconButton(onClick = onClickOfCalender) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "Calender of Month",
//                    tint = MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            }
        }
    )
}