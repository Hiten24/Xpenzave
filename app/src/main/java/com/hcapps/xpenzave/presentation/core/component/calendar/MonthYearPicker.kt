package com.hcapps.xpenzave.presentation.core.component.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.maxkeppeker.sheets.core.utils.TestTags
import com.maxkeppeler.sheets.core.R
import java.time.LocalDate

private val months = listOf("Jan","Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

@Composable
fun MonthDialog(
    selectedMonth: Int,
    selectedYear: Int,
    onDismiss: () -> Unit,
    onSelectMonthYear: (LocalDate) -> Unit
) {

    Dialog(onDismissRequest = onDismiss,) {
        Surface(
            modifier = Modifier
                .testTag(TestTags.DIALOG_BASE_CONTENT)
                .fillMaxWidth()
                .animateContentSize(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SelectMonthContent(
                    selectedMonth = selectedMonth,
                    selectedYear = selectedYear,
                    onSelectMonth = { month, year ->
                        onSelectMonthYear(LocalDate.of(year, month, 1))
                    }
                )
            }
        }
    }
}

@Composable
private fun SelectMonthContent(
    selectedMonth: Int,
    selectedYear: Int,
    onSelectMonth: (month: Int, year: Int) -> Unit
) {

    val todayMonth = LocalDate.now().monthValue
    val todayYear = LocalDate.now().year
    var currentYear by remember { mutableStateOf(selectedYear) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SelectMonthTopBar(
            year = currentYear,
            onPrev = { currentYear -= 1 },
            onNext = { if (currentYear != todayYear) { currentYear += 1 } },
            nextDisabled = currentYear == todayYear
        )

        val clickableRange = if (todayYear == currentYear) IntRange(0, todayMonth - 1) else IntRange(0, 11)
        val range = IntRange(0, 11)

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            mainAxisSpacing = 18.dp,
            crossAxisSpacing = 8.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Start
        ) {
            range.forEach { index ->
                val textColor = if (index + 1 == selectedMonth && currentYear == selectedYear) MaterialTheme.colorScheme.surface else if (!clickableRange.contains(index)) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface
                val containerColor =
                    if (index + 1 == selectedMonth && currentYear == selectedYear) MaterialTheme.colorScheme.primary else Color.Transparent
                Box(
                    modifier = Modifier
                        .background(containerColor, MaterialTheme.shapes.small)
                        .clip(MaterialTheme.shapes.small)
                        .clickable(enabled = clickableRange.contains(index)) {
//                            selectedMonth = index + 1
//                            selectedYear = currentYear
                            onSelectMonth(index + 1, currentYear)
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = months[index], color = textColor)
                }
            }
        }

    }

}

@Composable
fun SelectMonthTopBar(
    year: Int,
    prevDisabled: Boolean = false,
    nextDisabled: Boolean = false,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    val enterTransition = expandIn(expandFrom = Alignment.Center, clip = false) + fadeIn()
    val exitTransition = shrinkOut(shrinkTowards = Alignment.Center, clip = false) + fadeOut()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        AnimatedVisibility(
            visible = !prevDisabled,
            enter = enterTransition,
            exit = exitTransition
        ) {
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.size(dimensionResource(R.dimen.scd_size_200)),
                enabled = !prevDisabled,
                onClick = onPrev
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(R.dimen.scd_size_150)),
                    imageVector = Icons.Outlined.ChevronLeft,
                    contentDescription = null
                )
            }
        }

        Text(
            modifier = Modifier.weight(1f),
            text = year.toString(),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(
            visible = !nextDisabled,
            enter = enterTransition,
            exit = exitTransition
        ) {
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.size(dimensionResource(R.dimen.scd_size_200)),
                enabled = !nextDisabled,
                onClick = onNext
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(R.dimen.scd_size_150)),
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null
                )
            }
        }
    }
}