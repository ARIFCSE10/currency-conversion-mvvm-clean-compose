package com.badsha.currencyconversion.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.badsha.currencyconversion.presentation.screen.Screen
import com.badsha.currencyconversion.presentation.screen.home.HomeScreen
import com.badsha.currencyconversion.presentation.ui.theme.CurrencyConversionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConversionTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {

                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyConversionTheme {
        Greeting("Android")
    }
}