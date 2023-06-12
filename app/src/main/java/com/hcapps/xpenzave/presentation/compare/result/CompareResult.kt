package com.hcapps.xpenzave.presentation.compare.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.compare.result.component.compare_garph.CompareResultCard
import com.hcapps.xpenzave.presentation.compare.result.component.comprea_expense_category_graph.CompareCategoryExpensesGraph
import com.hcapps.xpenzave.presentation.compare.result.component.expense_category_graph.CategoryData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareResult(
    onNavigateUp: () -> Unit
) {

    val lazyState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.compare))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back Arrow")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValue ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValue)
        ) {
            CompareHeader(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp))
            CompareResultContent(
                modifier = Modifier.fillMaxWidth(),
                lazyState
            )
        }
    }
}

@Composable
fun CompareResultContent(modifier: Modifier = Modifier, lazyState: LazyListState) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyState
    ) {

        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            CompareResultCard(
                title = "Budget",
                firstProgress = 0.8f,
                secondProgress = 1f,
                firstValue = "1000",
                secondValue = "1025",
                firstDate = "Sept 2019",
                secondDate = "Oct 2019"
            )
        }

        item {
            CompareResultCard(
                title = "Expense",
                firstProgress = 0.8f,
                secondProgress = 1f,
                firstValue = "1000",
                secondValue = "1025",
                firstDate = "Sept 2019",
                secondDate = "Oct 2019"
            )
        }
        item {
            CompareCategoryExpensesGraph(
                categories = CategoryData.defaultCompareCategoryGraphs()
            )
        }

    }
}

@Composable
fun CompareHeader(
    modifier: Modifier = Modifier,
    borderWidth: Dp = 2.dp,
    borderColor: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedCard(
        modifier = modifier,
        border = BorderStroke(borderWidth, color = borderColor.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderItem(month = "Sept", year = "2019", indicatorColor = MaterialTheme.colorScheme.inversePrimary)
            Divider(modifier = Modifier
                .height(26.dp)
                .width(1.dp))
            HeaderItem(month = "Oct", year = "2019")
        }
    }
}

@Composable
fun HeaderItem(
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    month: String,
    year: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompareIndicator(color = indicatorColor)
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = month,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = year,
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun CompareIndicator(
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 10.dp
) {
    Box(modifier = Modifier
        .size(size)
        .background(color, CircleShape))
}

@Preview
@Composable
fun PreviewHeaderItem() {
    HeaderItem(month ="Sept", year = "2019")
}


@Preview
@Composable
fun PreviewCompareHeader() {
    CompareHeader(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp))
}