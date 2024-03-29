package com.hcapps.xpenzave.presentation.compare.result.component.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryBarChartData
import com.hcapps.xpenzave.util.vertical

@Composable
fun Graph(
    modifier: Modifier = Modifier,
    style: GraphStyle = GraphStyle.defaultGraphStyle(),
    items: List<CategoryBarChartData>,
    state: GraphState,
    onSelect: (index: Int) -> Unit
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(style.spaceBetweenGraphBar, Alignment.CenterHorizontally)
    ) {
        items.forEachIndexed { index, item ->
            item {
                GraphBarSelector(
                    bar = item,
                    isSelected = index == state.selectedGraphBar,
                    onSelect = { onSelect(index) },
                    style = style
                )
            }
        }
    }
}

@Composable
private fun GraphBarSelector(
    modifier: Modifier = Modifier,
    style: GraphStyle,
    isSelected: Boolean,
    bar: CategoryBarChartData,
    onSelect: () -> Unit
) {

    val backgroundColor = if (isSelected) style.selectedIndicatorColor else Color.Transparent
    val borderColor = if (isSelected) style.selectorBorderTint.copy(alpha = 0.3f) else Color.Transparent

    Box(
        modifier = modifier
            .vertical()
            .rotate(90f)
            .background(backgroundColor, CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .height(style.barSelectorWidth)
            .padding(start = 16.dp, end = 4.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
//                category.expense.forEachIndexed { index, expenseData ->
//                    val barColor = if (index == 1) style.secondBarColor else style.barColor
                GraphBar(percentage = bar.budgetPercentageByCategory, style = style, color = style.barColor)
//                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier.width(80.dp),
                text = bar.category.name,
                style = style.textStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun GraphBar(
    modifier: Modifier = Modifier,
    percentage: Int = 100,
    style: GraphStyle,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(style.barSize) // bar container is rotated to vertically
                .height(style.barWidth) // bar container is rotated to vertically
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage / 100f) // bar container is rotated to vertically
                    .fillMaxHeight()
                    .background(color, CircleShape)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview
@Composable
fun PreviewGraph() {
    Graph(
        items = CategoryBarChartData.defaultCategoryGraphs(),
        state = rememberGraphState(),
        onSelect = {}
    )
}

@Preview
@Composable
fun PreviewGraphBarContainer() {
    GraphBarSelector(
        isSelected = true,
        style = GraphStyle.defaultGraphStyle(),
        bar = CategoryBarChartData.dummyCategory()
    ) {

    }
}

@Preview
@Composable
fun PreviewGraphBar() {
    GraphBar(style = GraphStyle.defaultGraphStyle())
}