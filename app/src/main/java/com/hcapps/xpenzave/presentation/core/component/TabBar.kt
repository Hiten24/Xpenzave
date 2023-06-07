package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun XpenzaveTabRow(
    modifier: Modifier = Modifier
) {
    var selectedState by remember {
        mutableStateOf(0)
    }
    TabRow(
        selectedTabIndex = selectedState,
        modifier = modifier.clip(Shapes().small),
        containerColor = MaterialTheme.colorScheme.background,
        divider = {
            Divider(color = Color.Transparent)
        }
    ) {

        XpenzaveTab(
            text = "General",
            selected = selectedState == 0
        ) {
            if (selectedState != 0) selectedState = 0
        }
        XpenzaveTab(
            text = "Expense Log",
            selected = selectedState == 1
        ) {
            if (selectedState != 1) selectedState = 1
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

@Preview
@Composable
fun PreviewTab() {
//    XpenzaveTab("General", false, onClick = {})
}

@Preview
@Composable
fun PreviewTanBar() {
    XpenzaveTabRow()
}