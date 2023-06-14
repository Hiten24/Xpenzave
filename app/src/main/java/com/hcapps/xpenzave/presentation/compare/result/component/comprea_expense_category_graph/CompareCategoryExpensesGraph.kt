package com.hcapps.xpenzave.presentation.compare.result.component.comprea_expense_category_graph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.hcapps.xpenzave.presentation.compare.result.CompareIndicator
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryBarChartData
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.ExpenseDataForGraph
import com.hcapps.xpenzave.presentation.compare.result.component.graph.Graph
import com.hcapps.xpenzave.presentation.compare.result.component.graph.rememberGraphState
import com.hcapps.xpenzave.ui.theme.DefaultCardElevation

@Composable
fun CompareCategoryExpensesGraph(
    categories: List<CategoryBarChartData> = emptyList(),
    elevation: Dp = DefaultCardElevation,
    cardColor: Color = MaterialTheme.colorScheme.surface
) {

    val graphState = rememberGraphState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(elevation),
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
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

            /*CompareGraphIndicator(
                modifier = Modifier.fillMaxWidth(),
                expenseDatumForGraphs = categories[graphState.selectedGraphBar].expense
            )*/
        }
    }
}

@Composable
private fun CompareGraphIndicator(
    modifier: Modifier = Modifier,
    expenseDatumForGraphs: List<ExpenseDataForGraph>
) {
    Row(modifier = modifier) {
        CompareValue(
            modifier = Modifier.weight(1f),
            indicatorColor = MaterialTheme.colorScheme.inversePrimary,
            value = expenseDatumForGraphs.getOrNull(0)?.totalSpendingOfMonth.toString() + "$"
        )
        CompareValue(
            modifier = Modifier.weight(1f),
            value = expenseDatumForGraphs.getOrNull(0)?.totalSpendingOfMonth.toString() + "$"
        )
    }
}

@Composable
private fun CompareValue(
    modifier: Modifier = Modifier,
    value: String = "1000 $",
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
       CompareIndicator(color = indicatorColor)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PreviewCompareGraphIndicator() {
    CompareGraphIndicator(expenseDatumForGraphs = emptyList())
}

@Preview
@Composable
fun PreviewCategoryExpensesGraph() {
    CompareCategoryExpensesGraph(categories = CategoryBarChartData.defaultCategoryGraphs())
}