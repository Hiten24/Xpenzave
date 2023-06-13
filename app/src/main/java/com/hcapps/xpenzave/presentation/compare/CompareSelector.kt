package com.hcapps.xpenzave.presentation.compare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.presentation.compare.component.scroll_wheel_picker.ScrollWheelSelector
import com.hcapps.xpenzave.presentation.compare.component.scroll_wheel_picker.ScrollWheelStyle.Companion.defaultWheelPickerStyle
import com.hcapps.xpenzave.presentation.compare.component.scroll_wheel_picker.rememberScrollWheelSelectedState
import com.hcapps.xpenzave.presentation.core.component.button.XpenzaveButton

private val listOfMonths = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
private val listOfYears = listOf("1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareSelector(
    navigateCompareResult: () -> Unit,
    navigateUp: () -> Unit
) {

    val initSelectedYear = listOfYears.indexOf("2023")
    val initSelectedMonth = listOfMonths.indexOf("Jun")

    val firstSelectedMonth = rememberScrollWheelSelectedState()
    val secondSelectedMonth = rememberScrollWheelSelectedState()
    val firstSelectedYear = rememberScrollWheelSelectedState()
    val secondSelectedYear = rememberScrollWheelSelectedState()

    val indicatorSize by remember {
        mutableStateOf(10.dp)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.compare))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = "Close")
                    }
                }
            ) 
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                ScrollWheelSelector(
                    items = listOfYears,
                    modifier = Modifier.weight(1f),
                    startIndex = initSelectedYear,
                    visibleItemsCount = 3,
                    selectedState = firstSelectedYear,
                    style = defaultWheelPickerStyle(
                        textStyle = MaterialTheme.typography.titleMedium,
                        indicatorColor = Color.Transparent,
                        indicatorSize = indicatorSize
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                ScrollWheelSelector(
                    items = listOfYears,
                    modifier = Modifier.weight(1f),
                    startIndex = initSelectedYear,
                    visibleItemsCount = 3,
                    selectedState = secondSelectedYear,
                    style = defaultWheelPickerStyle(
                        textStyle = MaterialTheme.typography.titleMedium,
                        indicatorColor = Color.Transparent,
                        indicatorSize = indicatorSize
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                ScrollWheelSelector(
                    items = listOfMonths,
                    modifier = Modifier.weight(1f),
                    startIndex = initSelectedMonth,
                    visibleItemsCount = 5,
                    selectedState = firstSelectedMonth,
                    style = defaultWheelPickerStyle(indicatorSize = indicatorSize)
                )

                Spacer(modifier = Modifier.width(12.dp))

                ScrollWheelSelector(
                    items = listOfMonths,
                    modifier = Modifier.weight(1f),
                    startIndex = initSelectedMonth,
                    visibleItemsCount = 5,
                    selectedState = secondSelectedMonth,
                    style = defaultWheelPickerStyle(
                        indicatorColor = MaterialTheme.colorScheme.inversePrimary,
                        indicatorSize = indicatorSize
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            XpenzaveButton(
                title = stringResource(id = R.string.compare),
                modifier = Modifier.padding(22.dp),
                onClickOfButton = navigateCompareResult
            )

            Spacer(modifier = Modifier.height(22.dp))
                
        }
    }
}



