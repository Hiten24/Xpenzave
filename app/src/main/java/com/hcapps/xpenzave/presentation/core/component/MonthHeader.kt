package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.defaultDisplayName
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthHeader(
    modifier: Modifier = Modifier,
    date: LocalDate,
    icon: ImageVector,
    badge: Boolean = false,
    style: MonthHeaderStyle = MonthHeaderStyle.defaultMonthHeaderStyle(),
    onClickOfIcon: (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = date.month.defaultDisplayName(),
                style = style.monthTextStyle.copy(fontWeight = style.fontWeight),
                color = style.monthTextColor,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date.year.toString(),
                style = style.yearTextStyle,
                color = style.yearTextColor
            )
        }
        onClickOfIcon?.let {
            Box {
                IconButton(
                    onClick = it,
                    modifier = Modifier.align(Center),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Calendar Month",
                        tint = style.iconColor
                    )
                }
                if (badge) {
                    Badge(
                        modifier = Modifier
                            .align(TopStart)
                            .padding(top = 8.dp, start = 4.dp)
                            .size(8.dp)
                    )
                }
            }
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
            fontWeight: FontWeight = FontWeight.Bold
        ) = MonthHeaderStyle(monthTextColor, yearTextColor, monthTextStyle, yearTextStyle, iconColor, fontWeight)
    }
}