package com.hcapps.xpenzave.presentation.expense_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.hcapps.xpenzave.presentation.core.component.ZoomableImagePreview
import com.hcapps.xpenzave.presentation.edit_budget.BudgetScreenFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ExpenseDetailScreen(
    navigateUp: () -> Unit,
    viewModel: ExpenseDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    var imagePreviewOpened by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel.uiFlow) {
        viewModel.uiFlow.collectLatest {
            when (it) {
                is BudgetScreenFlow.SnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(it.message)
                    }
                }
                is BudgetScreenFlow.NavigateUp -> { navigateUp() }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopBar(
                date = state.date ?: LocalDate.now(),
                onClickOfNavigationIcon = navigateUp,
                deleteExpense = {
                    state.expenseId?.let { viewModel.deleteExpense(it) }
                }
            )
        }
    ) { paddingValues ->


        if (state.loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .height(2.dp),
                strokeCap = StrokeCap.Round
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp, horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExpenseDetailContent(
                state = state,
                previewImage = { imagePreviewOpened = true }
            )
        }

    }

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
fun ExpenseDetailContent(
    state: ExpenseDetailState,
    previewImage: () -> Unit
) {

    state.amount?.let {
        DetailsItem(text = "Amount") {
            Text(
                text = "$it $",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Divider()
    }

    state.category?.let {
        DetailsItem(text = "Category") {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = it.icon,
                    contentDescription = "category icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Divider()
    }

    state.photoId?.let {
        DetailsItem(text = "Photos") {
            Spacer(modifier = Modifier.height(2.dp))
            Card(
                modifier = Modifier
                    .size(100.dp)
                    .clickable(onClick = previewImage),
                elevation = CardDefaults.elevatedCardElevation(2.dp)
            ) {
                AsyncImage(
                    model = state.photo,
                    contentDescription = "Resource Photo",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Divider()
    }

    state.more?.let {
        DetailsItem(text = "More Details") {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    date: LocalDate = LocalDate.now(),
    onClickOfNavigationIcon: () -> Unit,
    deleteExpense: () -> Unit
) {
    Timber.i("date: $date")
    LargeTopAppBar(
        title = {
            Column(modifier = Modifier.padding(horizontal = 6.dp)) {
                Text(
                    text = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${date.dayOfMonth} ${date.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onClickOfNavigationIcon) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Arrow Back"
                )
            }
        },
        actions = {
            IconButton(onClick = deleteExpense) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Remove")
            }
        }
    )
}

@Composable
fun DetailsItem(
    text: String,
    value: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        value()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpenseDetailScreen() {
    ExpenseDetailScreen({})
}