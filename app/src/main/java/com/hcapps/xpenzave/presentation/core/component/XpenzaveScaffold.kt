package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.hcapps.xpenzave.util.Screen

private val bottomBarItems = listOf(
    BottomBarItem(icon =Icons.Outlined.Home, label = "Home", route = Screen.Home.route, description = "Article"),
    BottomBarItem(icon =Icons.Outlined.Article, label = "Expense", route = Screen.Stats.route, description = "Stats"),
    BottomBarItem(icon =Icons.Outlined.Settings, label = "Settings", route = Screen.Settings.route, description = "Settings"),
    BottomBarItem(icon =Icons.Outlined.Add, label = "Expense", route = Screen.AddExpense.route, description = "Expense")
)

private val bottomBarScreenList = listOf(
    Screen.Home.route,
    Screen.Stats.route,
    Screen.Settings.route,
//    Screen.AddExpense.route,
)

@Composable
fun XpenzaveScaffold(
    modifier: Modifier = Modifier,
    items: List<BottomBarItem> = bottomBarItems,
    onClickOfItem: (route: String) -> Unit,
    backStackEntry: State<NavBackStackEntry?>,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (shouldShowBottomBar(backStackEntry.value?.destination?.route)) {
                NavigationBar {
                    NavBarItem(
                        items = items,
                        onClickOfItem = onClickOfItem,
                        backStackEntry = backStackEntry
                    )
                }
            }
        }
    ) {
        content(it)
    }

}

@Composable
fun RowScope.NavBarItem(
    items: List<BottomBarItem>,
    onClickOfItem: (route: String) -> Unit,
    backStackEntry: State<NavBackStackEntry?>
) {
    items.forEach { item ->
        NavigationBarItem(
            selected = item.route == backStackEntry.value?.destination?.route,
            onClick = { onClickOfItem(item.route) },
            icon = {
                Icon(imageVector = item.icon, contentDescription = item.description)
            },
            label = {
                Text(text = item.label)
            }
        )
    }
}

private fun shouldShowBottomBar(destinationRoute: String?): Boolean {
    return destinationRoute in bottomBarScreenList
}