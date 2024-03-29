package com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.compare.result.component.graph.Graph
import com.hcapps.xpenzave.presentation.compare.result.component.graph.rememberGraphState
import com.hcapps.xpenzave.ui.theme.DefaultCardElevation

@Composable
fun CategoryExpensesGraph(
    categories: List<CategoryBarChartData> = emptyList(),
    cardColor: Color = MaterialTheme.colorScheme.surface,
    cardElevation: Dp = DefaultCardElevation
) {

    val graphState = rememberGraphState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(cardElevation),
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = stringResource(R.string.expenses_category),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Graph(
                items = categories,
                state = graphState,
                onSelect = { graphState.selectedGraphBar = it }
            )

            Divider()

            GraphIndicator(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 22.dp),
                category = categories[graphState.selectedGraphBar]
            )
        }
    }
}

@Composable
private fun GraphIndicator(
    modifier: Modifier,
    category: CategoryBarChartData
) {

//    val selectedExpense = category.expense.getOrNull(0) ?: ExpenseDataForGraph.emptyExpense()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "${category.totalSpendByCategory} $",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "/ ${category.budget} $",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        GraphProgress(percentage = category.budgetPercentageByCategory)

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = "${category.budgetPercentageByCategory}%",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun GraphProgress(
    indicatorSize: Dp = 62.dp,
    strokeWidth: Dp = 16.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    percentage: Int = 20
) {

    Box(
        modifier = Modifier
            .size(indicatorSize)
            .border(2.dp, color, CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(indicatorSize - (strokeWidth * 2))
                .border(2.dp, color, CircleShape)
        )

        CircularProgressIndicator(
            progress = percentage / 100f,
            modifier = Modifier.size(62.dp),
            strokeWidth = strokeWidth
        )
    }
}

@Preview
@Composable
fun PreviewGraphProgress() {
    GraphProgress()
}

@Preview
@Composable
fun PreviewCategoryExpensesGraph() {
    CategoryExpensesGraph(categories = CategoryBarChartData.defaultCategoryGraphs())
}

@Preview
@Composable
fun PreviewGraphIndicator() {
    GraphIndicator(modifier = Modifier.fillMaxWidth(), CategoryBarChartData.dummyCategory())
}
