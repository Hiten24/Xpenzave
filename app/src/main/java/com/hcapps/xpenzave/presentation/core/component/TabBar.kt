package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun XpenzaveTabRow(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit,
    items: List<String>,
    containerColor: Color = MaterialTheme.colorScheme.surface
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier.clip(Shapes().small),
        containerColor = containerColor,
        divider = {
            Divider(color = Color.Transparent)
        }
    ) {

        items.forEachIndexed { index, item ->
            XpenzaveTab(
                text = item,
                selected = selectedIndex == index
            ) {
                onSelectionChange(index)
            }
        }

    }
}

@Composable
fun XpenzaveTab(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Tab(
        modifier = modifier.clip(MaterialTheme.shapes.small),
        selected = selected,
        onClick = onClick,
        selectedContentColor = MaterialTheme.colorScheme.primary,
        unselectedContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        text = {
            Text(text = text)
        }
    )
}