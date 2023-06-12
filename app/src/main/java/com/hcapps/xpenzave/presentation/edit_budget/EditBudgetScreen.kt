package com.hcapps.xpenzave.presentation.edit_budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.core.component.MonthHeader
import com.hcapps.xpenzave.presentation.core.component.MonthHeaderStyle
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton
import com.hcapps.xpenzave.ui.theme.DefaultCardElevation

@Composable
fun EditBudgetScreen(
    navigateUp: () -> Unit
) {

    var budgetValue by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = DefaultCardElevation),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            MonthHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                month = "September",
                year = "2019",
                icon = Icons.Outlined.Close,
                style = MonthHeaderStyle.defaultMonthHeaderStyle(iconColor = MaterialTheme.colorScheme.onSurface),
                onClickOfIcon = navigateUp
            )

            BudgetTextField(
                modifier = Modifier.fillMaxSize(),
                budgetValue = budgetValue,
                onBudgetChange = {
                    budgetValue = it
                }
            )
        }

        XpenzaveButton(
            modifier = Modifier.padding(horizontal = 28.dp),
            title = "Save"
        ) {

        }

    }
}

@Composable
fun BudgetTextField(
    modifier: Modifier = Modifier,
    budgetValue: String,
    onBudgetChange: (String) -> Unit,
    inputTextAlign: TextAlign = TextAlign.Center,
    inputTextColor: Color = MaterialTheme.colorScheme.primary,
    inputTextStyle: TextStyle = MaterialTheme.typography.headlineSmall
) {
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 58.dp)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.budget),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = textFieldModifier,
            value = budgetValue,
            onValueChange = onBudgetChange,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = inputTextColor,
                unfocusedTextColor = inputTextColor
            ),
            textStyle = inputTextStyle.copy(textAlign = inputTextAlign, fontWeight = FontWeight.Medium),
            placeholder = {
                Text(
                    modifier = textFieldModifier,
                    text = "2000",
                    color = inputTextColor.copy(alpha = 0.5f),
                    style = inputTextStyle.copy(
                        textAlign = inputTextAlign,
                        fontWeight = FontWeight.Medium
                    )
                )
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        )
    }
}

@Preview
@Composable
fun PreviewBudgetTextField() {
    BudgetTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onBudgetChange = {},
        budgetValue = "2000"
    )
}

@Preview
@Composable
fun PreviewEditBudgetScreen() {
    EditBudgetScreen() {}
}