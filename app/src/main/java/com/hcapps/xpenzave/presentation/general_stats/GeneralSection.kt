package com.hcapps.xpenzave.presentation.general_stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.home.component.BudgetProgressCard

@Composable
fun GeneralSection() {
    Column(modifier = Modifier.fillMaxSize()) {
        BudgetProgressCard(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .padding(16.dp),
            onClickOfEditBudget = {},
            onClickOfCalendar = {}
        )
    }
}