package com.hcapps.xpenzave.presentation.compare.result.component.comprea_expense_category_graph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.compare.result.CompareIndicator
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryData
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.ExpenseData
import com.hcapps.xpenzave.presentation.compare.result.component.graph.Graph
import com.hcapps.xpenzave.presentation.compare.result.component.graph.rememberGraphState

@Composable
fun CompareCategoryExpensesGraph(
    categories: List<CategoryData> = emptyList()
) {

    val graphState = rememberGraphState()

    Card(
        modifier = Modifier.fillMaxWidth()
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

            CompareGraphIndicator(
                modifier = Modifier.fillMaxWidth(),
                expenseData = categories[graphState.selectedGraphBar].expense
            )
        }
    }
}

@Composable
private fun CompareGraphIndicator(
    modifier: Modifier = Modifier,
    expenseData: List<ExpenseData>
) {
    Row(modifier = modifier) {
        CompareValue(
            modifier = Modifier.weight(1f),
            indicatorColor = MaterialTheme.colorScheme.inversePrimary,
            value = expenseData.getOrNull(0)?.totalSpendingOfMonth.toString() + "$"
        )
        CompareValue(
            modifier = Modifier.weight(1f),
            value = expenseData.getOrNull(0)?.totalSpendingOfMonth.toString() + "$"
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
    CompareGraphIndicator(expenseData = emptyList())
}

@Preview
@Composable
fun PreviewCategoryExpensesGraph() {
    CompareCategoryExpensesGraph(categories = CategoryData.defaultCategoryGraphs())
}