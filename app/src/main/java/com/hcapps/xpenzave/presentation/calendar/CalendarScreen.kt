package com.hcapps.xpenzave.presentation.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.core.component.MonthHeader
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.maxkeppeker.sheets.core.icons.LibIcons
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navigateUp: () -> Unit
) {

    var selectedDate: LocalDate by remember {
        mutableStateOf(LocalDate.now())
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        MonthHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp),
            date = LocalDate.now(),
            icon = Icons.Outlined.Close,
            onClickOfIcon = navigateUp
        )

        CalendarView(
            sheetState = rememberSheetState(),
            selection = CalendarSelection.Date(
                withButtonView = false,
                onSelectDate = { date -> selectedDate = date }
            ),
            config = CalendarConfig(
                style = CalendarStyle.MONTH,
                monthSelection = true,
                yearSelection = true,
                icons = LibIcons.Outlined
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        XpenzaveButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 18.dp),
            title = "Select"
        ) {

        }

    }
}