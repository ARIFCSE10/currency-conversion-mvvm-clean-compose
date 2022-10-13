package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.badsha.currencyconversion.domain.model.Currency


@Composable
fun AvailableCurrencyListComponent(currencies: List<Currency>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,

        ) {
        Text(
            text = "My Balance",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(count = currencies.size) { index ->
                AvailableCurrencyItemComponent(currencies[index])
            }

        }
    }
}