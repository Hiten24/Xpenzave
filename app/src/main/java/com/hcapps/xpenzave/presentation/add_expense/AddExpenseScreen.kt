package com.hcapps.xpenzave.presentation.add_expense

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.add_expense.Category.Companion.dummies
import com.hcapps.xpenzave.presentation.core.component.CategoryComponent
import com.hcapps.xpenzave.presentation.core.component.CategoryStyle
import com.hcapps.xpenzave.presentation.core.component.CategoryStyle.Companion.defaultCategoryStyle
import com.hcapps.xpenzave.presentation.core.component.XpenzaveButton
import com.hcapps.xpenzave.presentation.home.component.LargeButton
import com.hcapps.xpenzave.ui.theme.BorderWidth
import com.hcapps.xpenzave.ui.theme.ButtonHeight
import com.hcapps.xpenzave.ui.theme.headerBorderAlpha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(
    navigateUp: () -> Unit
) {

    var image by remember { mutableStateOf<Uri?>(null) }

    Scaffold(
        topBar = {
            AddExpenseTopBar(onClickOfNavigationIcon = navigateUp)
        }
    ) { paddingValue ->

        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValue)
        ) {
            AddExpenseContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = ButtonHeight),
                image = image
            ) { uri -> image = uri }

            Box(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .align(Alignment.BottomCenter)) {
                XpenzaveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                    title = stringResource(R.string.add),
                    onClickOfButton = {}
                )
            }
        }
    }
}

@Composable
fun AddExpenseContent(
    modifier: Modifier = Modifier,
    image: Uri?,
    onImageSelect: (Uri?) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
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
            AdditionalInfoCard(
                modifier = Modifier.fillMaxWidth(),
                cardColor = MaterialTheme.colorScheme.background
            ) {
                SelectCategoryComponent(
                    categoryStyle = defaultCategoryStyle(backgroundColor = MaterialTheme.colorScheme.surface)
                )
                AddBillEachMonth(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.shapes.small
                        ),
                    checked = false,
                    onCheckedChange = { }
                )
                AddPhotoSection(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    image = image,
                    onImageSelect = onImageSelect
                )
                MoreDetailsSection(modifier = Modifier.padding(horizontal = 6.dp))
                /*XpenzaveButton(
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                    title = stringResource(R.string.add),
                    onClickOfButton = {}
                )*/
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
    cardColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
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
            content()
        }
    }
}

@Composable
fun SelectCategoryComponent(
    modifier: Modifier = Modifier,
    categories: List<Category> = dummies(),
    categoryStyle: CategoryStyle = defaultCategoryStyle()
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
                        category = category,
                        style = categoryStyle
                    ) {}
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
fun AddPhotoSection(
    modifier: Modifier = Modifier,
    image: Uri? = null,
    onImageSelect: (image: Uri?) -> Unit
) {

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = onImageSelect
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        Text(text = "Add Photo", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            if (image == null) {
                LargeButton(
                    modifier = Modifier.shadow(1.dp, shape = CircleShape),
                    buttonColor = MaterialTheme.colorScheme.onPrimary,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onClickOfAddExpense = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            } else {
                Card(
                    modifier = Modifier.size(100.dp),
                    elevation = CardDefaults.elevatedCardElevation(2.dp)
                ) {
                    AsyncImage(
                        model = image,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Uploaded Bill"
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            image?.let {
                TextButton(
                    onClick = { onImageSelect(null) },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "Clear",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

        }

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