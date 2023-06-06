package com.hcapps.xpenzave.presentation.add_expense

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.ui.theme.BorderWidth
import com.hcapps.xpenzave.ui.theme.headerBorderAlpha

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddExpense() {

    var addBillEachMonthSwitchState by remember { mutableStateOf(false) }
//    val cardColor = MaterialTheme.colorScheme.surface
    val cardColor = Color.Red
    val padding = 14.dp

    Scaffold(
        topBar = { AddExpenseTopBar(
            onClickOfNavigationIcon = {}
        )  }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = it.calculateTopPadding()),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(12.dp)
        ) {

            item(span = { GridItemSpan(3) }) {
                AmountSection(
                    modifier = Modifier.padding(horizontal = padding),
                    amount = "",
                    onAmountChange = {}
                )
            }

            // empty space
            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(22.dp)) }

            item(span = { GridItemSpan(3) }) {
                DateSection(modifier = Modifier.padding(horizontal = padding)) {

                }
            }

            // empty space
            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(11.dp)) }

            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(11.dp).background(cardColor)) }

            item(span = { GridItemSpan(3) }) {
                Text(
                    text = stringResource(R.string.select_category),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.background(cardColor).padding(horizontal = padding)
                )
            }

            // empty space
            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(22.dp).background(cardColor)) }

            items(
                items = CategoryData.dummies(),
                span = { GridItemSpan(1) }
            ) { category ->
                Box(modifier = Modifier.background(cardColor)) {
                    Category(modifier = Modifier.padding(8.dp), category = category)
                }
            }

            // empty space
            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(22.dp).background(cardColor)) }

            item(span = { GridItemSpan(3) }) {
                AddBillEachMonth(
                    modifier = Modifier.background(cardColor).padding(horizontal = padding),
                    checked = addBillEachMonthSwitchState,
                    onCheckedChange = { checked ->
                        addBillEachMonthSwitchState = checked
                    }
                )
            }

            // empty space
            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(22.dp).background(cardColor)) }

            item(span = { GridItemSpan(3) }) {
                AddPhotoSection(modifier = Modifier.background(cardColor).padding(horizontal = padding))
            }

            // empty space
            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(22.dp).background(cardColor)) }

            item(span = { GridItemSpan(3) }) {
                MoreDetailsSection(modifier = Modifier.background(cardColor).padding(horizontal = padding))
            }

            item(span = { GridItemSpan(3) }) { Spacer(modifier = Modifier.height(22.dp).background(cardColor)) }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseTopBar(
    onClickOfNavigationIcon: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.background(
            brush = Brush.horizontalGradient(
                listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )
        ),
        title = { Text(
            text = "Add Expense",
            color = MaterialTheme.colorScheme.onPrimary
        ) },
        navigationIcon = {
            IconButton(onClick = onClickOfNavigationIcon) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back Arrow",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun AmountSection(
    modifier: Modifier = Modifier,
    amount: String,
    onAmountChange: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.amount),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = amount,
            onValueChange = onAmountChange,
            textStyle = MaterialTheme.typography.headlineLarge,
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter),
                    style = MaterialTheme.typography.headlineLarge,
                )
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
    }
}

@Composable
fun DateSection(
    modifier: Modifier = Modifier,
    date: String = "Tuesday, 25 September",
    onClickOfCalenderIcon: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = onClickOfCalenderIcon) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = "Calendar Of Month",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun Category(modifier: Modifier = Modifier, category: CategoryData) {

    var isSelected by remember { mutableStateOf(category.isSelected) }

    val borderColorAlpha = if (isSelected) 1f else 0.1f
    val iconColorAlpha = if (isSelected) 1f else 0.6f

    OutlinedCard(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape = Shapes().small)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { isSelected = !isSelected },
        border = BorderStroke(BorderWidth, MaterialTheme.colorScheme.primary.copy(alpha = borderColorAlpha)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = category.icon,
                contentDescription = "Icon",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = iconColorAlpha)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary.copy(alpha = iconColorAlpha)
            )
        }
    }
}

@Composable
fun AddBillEachMonth(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes().small)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.primary.copy(alpha = headerBorderAlpha),
                shape = Shapes().small
            )
            .padding(vertical = 8.dp)
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.add_this_bill_each_month),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun AddPhotoSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Add Photo", style = MaterialTheme.typography.titleMedium)

    }
}

@Composable
fun MoreDetailsSection(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "More Details", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(18.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = Shapes().small,
            placeholder = {
                Text(text = stringResource(R.string.enter_here))
            },
            value = "",
            onValueChange = {}
        )
    }
}

@Preview
@Composable
fun PreviewAddExpense() {
    AddExpense()
}

/*
@Preview
@Composable
fun PreviewAmountSection() {
    AmountSection("") {}
}

@Preview
@Composable
fun PreviewDateSection() {
    DateSection {}
}

@Preview
@Composable
fun PreviewSelectCategorySection() {
//    SelectCategorySection()
}

@Preview
@Composable
fun PreviewCategory() {
    Category(category = CategoryData("Pizza", Icons.Outlined.LocalPizza, false))
}

@Preview
@Composable
fun PreviewAddBillEachMonth() {
    AddBillEachMonth(false) {}
}

@Preview
@Composable
fun PreviewAddPhotoSection() {
    AddPhotoSection()
}

@Preview
@Composable
fun PreviewMoreDetailsSection() {
    MoreDetailsSection()
}

@Preview
@Composable
fun PreviewTopBar() {
    AddExpenseTopBar(onClickOfNavigationIcon = {})
}
*/
