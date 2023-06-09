package com.hcapps.xpenzave.presentation.compare.result.component.compare_garph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.compare.result.CompareIndicator
import com.hcapps.xpenzave.ui.theme.DefaultCardElevation

@Composable
fun CompareResultCard(
    modifier: Modifier = Modifier,
    title: String,
    firstProgress: Float,
    secondProgress: Float,
    firstValue: String,
    secondValue: String,
    firstDate: String,
    secondDate: String,
    elevation: Dp = DefaultCardElevation,
    cardColor: Color = MaterialTheme.colorScheme.surface
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(elevation),
        colors = CardDefaults.cardColors(cardColor)
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(48.dp))

            CompareProgressContainer(
                firstProgress,
                secondProgress
            )

            Spacer(modifier = Modifier.height(56.dp))

            EntryContainer(
                firstDate = firstDate,
                secondDate = secondDate,
                firstValue = firstValue,
                secondValue = secondValue
            )

        }
    }
}

@Composable
fun CompareProgressContainer(
    firstProgress: Float,
    secondProgress: Float
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CompareProgress(firstProgress, MaterialTheme.colorScheme.inversePrimary)
        Spacer(modifier = Modifier.height(8.dp))
        CompareProgress(secondProgress)
    }
}

@Composable
fun CompareProgress(
    progress: Float,
    color: Color = MaterialTheme.colorScheme.primary
) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp),
        progress = progress,
        color = color,
        strokeCap = StrokeCap.Round
    )
}

@Composable
fun EntryContainer(
    firstDate: String,
    secondDate: String,
    firstValue: String,
    secondValue: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        EntryItem(
            indicatorColor = MaterialTheme.colorScheme.inversePrimary,
            title = firstDate,
            value = firstValue
        )
        Spacer(modifier = Modifier.height(8.dp))
        EntryItem(
            indicatorColor = MaterialTheme.colorScheme.primary,
            title = secondDate,
            value = secondValue
        )
    }
}

@Composable
fun EntryItem(
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    title: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompareIndicator(color = indicatorColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun PreviewCompareResultCard() {
    CompareResultCard(
        modifier = Modifier.padding(16.dp),
        title = "Budget",
        0.8f,
        1f,
        "1000",
        "1025",
        "Sept 2019",
        "Oct 2019"
    )
}