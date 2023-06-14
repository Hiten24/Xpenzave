package com.hcapps.xpenzave.presentation.add_expense

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.domain.model.category.Category.Companion.dummies
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseEvent.*
import com.hcapps.xpenzave.presentation.add_expense.state.AddExpenseState
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.core.component.CategoryComponent
import com.hcapps.xpenzave.presentation.core.component.CategoryStyle
import com.hcapps.xpenzave.presentation.core.component.CategoryStyle.Companion.defaultCategoryStyle
import com.hcapps.xpenzave.presentation.core.component.ZoomableImagePreview
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.presentation.core.component.calendar.SelectDateTimeDialog
import com.hcapps.xpenzave.presentation.home.component.LargeButton
import com.hcapps.xpenzave.ui.theme.BorderWidth
import com.hcapps.xpenzave.ui.theme.ButtonHeight
import com.hcapps.xpenzave.ui.theme.headerBorderAlpha
import com.hcapps.xpenzave.util.getActualPathOfImage
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AddExpense(
    navigateUp: () -> Unit,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {

    val state by viewModel.state
    val context = LocalContext.current
    val dateState = rememberSheetState()
    var imagePreviewOpened by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Loading -> {  }
                is UIEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UIEvent.Error -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            AddExpenseTopBar(onClickOfNavigationIcon = navigateUp)
        }
    ) { paddingValue ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValue),
        ) {

            AddExpenseContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = ButtonHeight + 28.dp),
                state = state,
                onAmountChange = { viewModel.onEvent(AmountChange(it)) },
                onClickOfCalenderIcon = { dateState.show() },
                onSelectCategory = { viewModel.onEvent(CategoryChange(it)) },
                onAddBillEachMonthChange = { viewModel.onEvent(AddBillEachMonthChange) },
                onPhotoChange = { uri -> viewModel.onEvent(PhotoChange(uri, context.getActualPathOfImage(uri))) },
                onPhotoClear = { viewModel.onEvent(ClearPhoto) },
                onDetailChange = { viewModel.onEvent(DetailsChange(it)) },
                previewPhoto = { imagePreviewOpened = true }
            )

            XpenzaveButton(
                modifier = Modifier.padding(start = 32.dp, bottom = 16.dp, end = 32.dp).align(
                    Alignment.BottomCenter),
                title = stringResource(R.string.add),
                state = state.addButtonState
            ) {
                viewModel.onEvent(AddButtonClicked)
            }

        }
    }

    SelectDateTimeDialog(
        dateState = dateState,
        selectedDateTime = state.date,
        onSelectDateTime = { viewModel.onEvent(DateTimeChange(it)) }
    )

    AnimatedVisibility(visible = imagePreviewOpened) {
        Dialog(onDismissRequest = { imagePreviewOpened = false }) {
            state.photo?.let {
                ZoomableImagePreview(
                    image = it,
                    onCloseClicked = { imagePreviewOpened = false },
                )
            }
        }
    }

}

@Composable
fun AddExpenseContent(
    modifier: Modifier = Modifier,
    state: AddExpenseState,
    onAmountChange: (String) -> Unit,
    onClickOfCalenderIcon: () -> Unit,
    onSelectCategory: (id: String) -> Unit,
    onAddBillEachMonthChange: (Boolean) -> Unit,
    onPhotoChange: (Uri?) -> Unit,
    onPhotoClear: () -> Unit,
    onDetailChange: (String) -> Unit,
    previewPhoto: () -> Unit
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
                amount = state.amount,
                error = state.amountError,
                onAmountChange = onAmountChange
            )
        }

        item(span = { GridItemSpan(3) }) {
            DateSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                date = state.date.toLocalDate(),
                onClickOfCalenderIcon = onClickOfCalenderIcon
            )
        }

        item(span = { GridItemSpan(3) }) {
            AdditionalInfoCard(
                modifier = Modifier.fillMaxWidth(),
                cardColor = MaterialTheme.colorScheme.background
            ) {
                SelectCategoryComponent(
                    categoryStyle = defaultCategoryStyle(backgroundColor = MaterialTheme.colorScheme.surface),
                    selected = state.category,
                    onSelect = onSelectCategory
                )
                AddBillEachMonth(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.shapes.small
                        ),
                    checked = state.eachMonth,
                    onCheckedChange = onAddBillEachMonthChange
                )
                AddPhotoSection(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    state.photo,
                    progress = state.uploadPhotoProgress,
                    onImageSelect = onPhotoChange,
                    onClearPhoto = onPhotoClear,
                    previewPhoto = previewPhoto
                )
                MoreDetailsSection(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    value = state.details,
                    onValueChange = onDetailChange
                )
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
    onAmountChange: (String) -> Unit,
    error: String? = null
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
            isError = error != null,
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
            ),
            trailingIcon = {
                 error?.let { Icon(imageVector = Icons.Outlined.PriorityHigh, contentDescription = null) }
            },
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            ),
            supportingText = {
                error?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
            }
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
    categoryStyle: CategoryStyle = defaultCategoryStyle(),
    onSelect: (id: String) -> Unit,
    selected: String
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
                        style = categoryStyle,
                        isSelected = category.id == selected,
                        onSelect = onSelect,
                    )

                }

            }

        }
    }
}

@Composable
fun DateSection(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onClickOfCalenderIcon: () -> Unit
) {

    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val month = date.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$dayOfWeek, ${date.dayOfMonth} $month",
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
    onImageSelect: (image: Uri?) -> Unit,
    onClearPhoto: () -> Unit,
    progress: Boolean = false,
    previewPhoto: () -> Unit
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
                    modifier = Modifier.size(100.dp).clickable(onClick = previewPhoto),
                    elevation = CardDefaults.elevatedCardElevation(2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        AsyncImage(
                            model = image,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Uploaded Bill"
                        )
                        if (progress) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(22.dp),
                                strokeWidth = 2.dp,
                                strokeCap = StrokeCap.Round
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            image?.let {
                TextButton(
                    onClick = onClearPhoto,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = stringResource(R.string.clear),
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
fun MoreDetailsSection(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
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
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
}