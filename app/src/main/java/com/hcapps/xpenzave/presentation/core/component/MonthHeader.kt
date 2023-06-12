package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.defaultDisplayName
import java.time.LocalDate

@Composable
fun MonthHeader(
    modifier: Modifier = Modifier,
    date: LocalDate,
    icon: ImageVector,
    style: MonthHeaderStyle = MonthHeaderStyle.defaultMonthHeaderStyle(),
    onClickOfIcon: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = date.month.defaultDisplayName(),
                style = style.monthTextStyle.copy(fontWeight = style.fontWeight),
                color = style.monthTextColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date.year.toString(),
                style = style.yearTextStyle,
                color = style.yearTextColor
            )
        }
        IconButton(onClick = onClickOfIcon) {
            Icon(
                imageVector = icon,
                contentDescription = "Calendar Month",
                tint = style.iconColor
            )
        }
    }
}

data class MonthHeaderStyle(
    val monthTextColor: Color,
    val yearTextColor: Color,
    val monthTextStyle: TextStyle,
    val yearTextStyle: TextStyle,
    val iconColor: Color,
    val fontWeight: FontWeight
) {
    companion object {
        @Composable
        fun defaultMonthHeaderStyle(
            monthTextColor: Color = MaterialTheme.colorScheme.primary,
            yearTextColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            monthTextStyle: TextStyle = MaterialTheme.typography.headlineSmall,
            yearTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
            iconColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            fontWeight: FontWeight = FontWeight.Medium
        ) = MonthHeaderStyle(monthTextColor, yearTextColor, monthTextStyle, yearTextStyle, iconColor, fontWeight)
    }
}