package com.hcapps.xpenzave.presentation.add_expense

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.add_expense.Category.Companion.dummies
import com.hcapps.xpenzave.presentation.core.component.CategoryComponent
import com.hcapps.xpenzave.presentation.core.component.XpenzaveButton
import com.hcapps.xpenzave.presentation.home.component.LargeButton
import com.hcapps.xpenzave.ui.theme.BorderWidth
import com.hcapps.xpenzave.ui.theme.headerBorderAlpha

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddExpense(
    navigateUp: () -> Unit
) {

    Scaffold(
        topBar = {
            AddExpenseTopBar(onClickOfNavigationIcon = navigateUp)
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = it.calculateTopPadding()),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(top = 24.dp, start = 12.dp, end = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            item(span = { GridItemSpan(3) }) {
                AmountSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    amount = "",
                    onAmountChange = {}
                )
            }

            item(span = { GridItemSpan(3) }) {
                DateSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {

                }
            }

            item(span = { GridItemSpan(3) }) {
                AdditionalInfoCard(modifier = Modifier.fillMaxWidth())
            }

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
                    imageVector = Icons.Outlined.Close,
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
    Column(modifier = modifier) {
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
fun AdditionalInfoCard(
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.surface
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Column(
            modifier = modifier
                .padding(12.dp)
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            SelectCategoryComponent()
            AddBillEachMonth(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .background(MaterialTheme.colorScheme.background),
                checked = false,
                onCheckedChange = { _ -> }
            )
            AddPhotoSection(modifier = Modifier.padding(horizontal = 6.dp))
            MoreDetailsSection(modifier = Modifier.padding(horizontal = 6.dp))
            XpenzaveButton(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                title = stringResource(R.string.add)
            ) {

            }
        }
    }
}

@Composable
fun SelectCategoryComponent(
    modifier: Modifier = Modifier,
    categories: List<Category> = dummies()
) {
    val chunkedCategories = categories.chunked(3)
    Column(modifier = modifier) {

        Text(
            text = "Select Category",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 6.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        chunkedCategories.forEach { columnCategories ->
            Row(modifier = modifier.fillMaxWidth()) {
                columnCategories.forEach { category ->
                    CategoryComponent(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp),
                        category = category
                    )
                }
            }
        }
    }
}

@Composable
fun DateSection(
    modifier: Modifier = Modifier,
    date: String = "Tuesday, 25 September",
    onClickOfCalenderIcon: () -> Unit
) {
    Row(
        modifier = modifier,
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
fun AddBillEachMonth(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .clip(Shapes().small)
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
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        Text(text = "Add Photo", style = MaterialTheme.typography.titleMedium)
        LargeButton(
            modifier = Modifier.shadow(1.dp, shape = CircleShape),
            buttonColor = MaterialTheme.colorScheme.onPrimary,
            iconColor = MaterialTheme.colorScheme.primary,
            onClickOfAddExpense = {}
        )
    }
}

@Composable
fun MoreDetailsSection(modifier: Modifier = Modifier) {
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