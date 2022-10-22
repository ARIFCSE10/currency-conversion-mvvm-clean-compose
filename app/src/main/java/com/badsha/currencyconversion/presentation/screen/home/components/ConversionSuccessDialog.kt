package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ConversionSuccessDialog(
    onDismiss: () -> Unit,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = title,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(text = "OK") }
        },
        text = content,
    )
}

@Composable
fun SnackbarDemo() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) { padding ->
        Button(onClick = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "This is your message", actionLabel = "Do something"
                )
            }
        }) {
            Text(text = "Click me!")
        }
    }
}