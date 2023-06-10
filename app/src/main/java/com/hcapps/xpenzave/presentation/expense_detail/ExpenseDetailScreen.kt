package com.hcapps.xpenzave.presentation.expense_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpenseDetailScreen(
    navigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column(modifier = Modifier.padding(horizontal = 6.dp)) {
                        Text(
                            text = "Monday",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "25 September",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = "Arrow Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp, horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DetailsItem(text = "Amount") {
                Text(
                    text = "500$",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Divider()

            DetailsItem(text = "Category") {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        imageVector = Icons.Outlined.Receipt,
                        contentDescription = "category icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Bills",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Divider()

            DetailsItem(text = "Photos") {
                Spacer(modifier = Modifier.height(2.dp))
                Card(
                    modifier = Modifier.size(100.dp),
                    elevation = CardDefaults.elevatedCardElevation(2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bill),
                        contentDescription = "bill",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Divider()

            DetailsItem(text = "More Details") {
                Text(
                    text = "Electricity Bill.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    }
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
    ExpenseDetailScreen() {}
}