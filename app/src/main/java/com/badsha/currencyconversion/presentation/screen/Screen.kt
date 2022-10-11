package com.badsha.currencyconversion.presentation.screen


sealed class Screen(val route: String) {
    object HomeScreen : Screen("homeScreen")
}