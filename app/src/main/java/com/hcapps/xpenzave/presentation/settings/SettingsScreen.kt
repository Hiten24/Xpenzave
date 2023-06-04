package com.hcapps.xpenzave.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.reflect.TypeToken
import com.hcapps.xpenzave.presentation.core.UIEvent
import com.hcapps.xpenzave.presentation.settings.model.Currency
import com.hcapps.xpenzave.util.jsonToValue
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit
) {

    val context = LocalContext.current
    var state by viewModel.state

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Error -> Toast.makeText(context, event.error.message, Toast.LENGTH_SHORT).show()
                is UIEvent.ShowMessage -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SettingsHeader(
            state.email,
            onCLickOfLogOut = { viewModel.logOut {
                Toast.makeText(context, "logging out", Toast.LENGTH_SHORT).show()
                navigateToAuth()
            } }
        )
        SettingsContent(
            onSelectOfCurrency = { code ->
                viewModel.setCurrencyPreference(code)
                state = state.copy(currencyCode = code)
            },
            selectedCurrency = state.currencyCode
        )
    }
}

@Composable
fun SettingsHeader(
    email: String,
    onCLickOfLogOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.primary
                    )
                )
            )
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        SettingsTopBar(onCLickOfLogOut)
        Spacer(modifier = Modifier.height(32.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "E-mail",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
fun SettingsTopBar(
    onCLickOfLogOut: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        OutlinedButton(
            onClick = { onCLickOfLogOut() },
            shape = Shapes().small
        ) {
            Text(text = "Log Out", color = Color.White)
        }
    }
}

@Composable
fun SettingsContent(
    onSelectOfCurrency: (code: String) -> Unit,
    selectedCurrency: String
) {

    var openedCurrencyDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 38.dp)
    ) {
        SettingItem(title = "Change Password", onClick = {}) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow Forward"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        SettingItem(title = "Use Face ID", onClick = {}) {

        }
        Spacer(modifier = Modifier.height(10.dp))
        SettingItem(title = "Currency", onClick = { openedCurrencyDialog = true }) {
            Text(
                text = selectedCurrency,
                fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    CurrencySelectionDialog(
        openedDialog = openedCurrencyDialog,
        onDismissRequest = { openedCurrencyDialog = false },
        onSelectOfCurrency = { code ->
            onSelectOfCurrency(code)
            openedCurrencyDialog = false
        }
    )

}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    endCompose: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(vertical = 12.dp))
        endCompose()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectionDialog(
    openedDialog: Boolean,
    onDismissRequest: () -> Unit,
    onSelectOfCurrency: (code: String) -> Unit
) {
    val context = LocalContext.current
    if (openedDialog) {
        AlertDialog(modifier = Modifier.padding(vertical = 64.dp), onDismissRequest = onDismissRequest) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface, shape = Shapes().small)
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Select your currencies", modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))
                val currenciesJson = context.assets.open("Currencies.json").bufferedReader().use { it.readText() }
                val type = object : TypeToken<Map<String, Currency>>() {}.type
                val currencies = jsonToValue<Map<String, Currency>>(currenciesJson, type).values.toList()
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(currencies) { currency ->
                        Text(
                            text = "${currency.code}  -  ${currency.name}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectOfCurrency(currency.code)
                                }
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewSettingsHeader() {
    SettingsHeader("test.account@gmail.com") {}
}

@Preview
@Composable
fun PreviewSettingsTopBar() {
    SettingsTopBar {}
}

