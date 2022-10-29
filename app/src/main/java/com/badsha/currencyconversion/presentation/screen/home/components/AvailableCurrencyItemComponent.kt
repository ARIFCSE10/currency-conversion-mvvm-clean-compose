package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.badsha.currencyconversion.domain.extension.roundTwoDeciaml
import com.badsha.currencyconversion.domain.model.Currency


@Composable
fun AvailableCurrencyItemComponent(currency: Currency) {
    Card(
        modifier = Modifier
            .size(100.dp, 100.dp)
            .padding(4.dp)
            .border(
                1.dp, MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.small
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = currency.name,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Start
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(4.dp)
            )
            Text(
                text = currency.available.roundTwoDeciaml().toString(),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}