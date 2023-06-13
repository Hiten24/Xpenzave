package com.hcapps.xpenzave.presentation.core.component.calendar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarTimeline
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateTimeDialog(
    dateState: SheetState,
    selectedDateTime: LocalDateTime,
    onSelectDateTime: (dateTime: LocalDateTime) -> Unit
) {

    var date = selectedDateTime.toLocalDate()
    val timeState = rememberSheetState()

    CalendarDialog(
        state = dateState,
        selection = CalendarSelection.Date(
            selectedDate = selectedDateTime.toLocalDate(),
            onSelectDate = {
                date = it
                timeState.show()
            }
        ),
        config = CalendarConfig(disabledTimeline = CalendarTimeline.FUTURE)
    )

    ClockDialog(
        state = timeState,
        selection = ClockSelection.HoursMinutes { hour, minute ->
            val dateTime = LocalDateTime.of(date.year, date.month, date.dayOfMonth, hour, minute)
            onSelectDateTime(dateTime)
        },
        config = ClockConfig(defaultTime = selectedDateTime.toLocalTime())
    )

}

