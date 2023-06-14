package com.hcapps.xpenzave.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.presentation.core.component.CategoryComponent
import com.hcapps.xpenzave.presentation.core.component.CategoryStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    navigateUp: () -> Unit,
    applyFilter: (filters: Array<String>) -> Unit,
    viewModel: FilterViewModel = hiltViewModel()
) {

//    var amount by viewModel.amount

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Filter by",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Arrow Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FilterCategory(
                categories = viewModel.categories,
                selectedCategories = viewModel.selectedCategory,
                onSelectCategory = { viewModel.onSelectChange(it) }
            )

            // beta release
            /*AmountSlider(
                amount.toFloat(),
                onValueChange = { amount = it.toInt() },
                100f..2000f
            )*/
            Spacer(modifier = Modifier.weight(1f))

            BottomButton(
                reset = { viewModel.onRest() },
                apply = {
                    if (viewModel.isFilterChanged()) {
                        applyFilter(viewModel.getSelectedCategoriesId())
                    } else {
                        navigateUp()
                    }
                }
            )

        }
    }
}

@Composable
private fun FilterCategory(
    modifier: Modifier = Modifier,
    categories: SnapshotStateList<Category>,
    categoryStyle: CategoryStyle = CategoryStyle.defaultCategoryStyle(),
    selectedCategories: SnapshotStateList<String>,
    onSelectCategory: (String) -> Unit
) {
    val chunkedCategories = categories.chunked(3)
    Column(modifier = modifier) {
        chunkedCategories.forEach { columnCategories ->
            Row(modifier = modifier.fillMaxWidth()) {
                columnCategories.forEach { category ->
                    CategoryComponent(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp),
                        style = categoryStyle,
                        category = category,
                        isSelected = selectedCategories.contains(category.id),
                    ) {
                        onSelectCategory(category.id)
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomButton(
    modifier: Modifier = Modifier,
    reset: () -> Unit,
    apply: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        FilledTonalIconButton(
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.small,
            onClick = reset
        ) {
            Text(text = "Reset")
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.small,
            onClick = apply
        ) {
            Text(text = "Apply")
        }
    }
}

/*@Composable
private fun AmountSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = stringResource(id = R.string.amount),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "$value $",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}*/


@Preview(showBackground = true)
@Composable
fun PreviewFilterScreen() {
    FilterScreen(navigateUp = {}, {})
}