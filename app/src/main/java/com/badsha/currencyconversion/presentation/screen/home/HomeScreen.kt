package com.badsha.currencyconversion.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Scaffold {
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

            }
        }
    }
}