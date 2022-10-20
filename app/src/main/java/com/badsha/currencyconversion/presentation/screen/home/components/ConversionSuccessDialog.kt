package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

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