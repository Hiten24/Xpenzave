package com.hcapps.xpenzave.util

sealed class Screen(val route: String) {

    object Authentication: Screen(route = "authentication_screen")

    object Home: Screen(route = "home_screen")

    object Settings: Screen(route = "settings_screen")

}
