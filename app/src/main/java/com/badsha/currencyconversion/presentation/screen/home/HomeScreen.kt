package com.badsha.currencyconversion.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badsha.currencyconversion.presentation.screen.home.components.AvailableCurrencyListComponent
import com.badsha.currencyconversion.presentation.screen.home.components.ConvertCurrencyComponent


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Scaffold(topBar = { topBar() }) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            if (state.isLoading) { // Loading Case
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = "Loading data",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.primary
                    )
                }

            } else if (state.error) {  // Error Case
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        state.errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.h5
                    )
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(Modifier.weight(1f)) {
                        item {
                            AvailableCurrencyListComponent(viewModel.availableCurrencies.value)
                            ConvertCurrencyComponent()
                        }
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(32.dp)
                    ) {
                        Text(text = "Convert", style = MaterialTheme.typography.button)
                    }
                }
            }
        }
    }
}


@Composable
fun topBar() {
    TopAppBar(elevation = 8.dp) {
        Text(
            "Currency Converter",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ComposablePreview() {
    var text by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize()) {

    }
}