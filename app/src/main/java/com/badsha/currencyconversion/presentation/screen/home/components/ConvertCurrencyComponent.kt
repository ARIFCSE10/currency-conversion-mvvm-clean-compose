package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badsha.currencyconversion.presentation.screen.home.HomeViewModel

@Composable
fun ConvertCurrencyComponent(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val selling = viewModel.sellingCurrency
    val sellable = viewModel.sellableCurrencies
    val sellAmount = viewModel.sellAmount

    val buying = viewModel.buyingCurrency
    val buyable = viewModel.buyableCurrencies
    val buyAmount = viewModel.buyAmount

    val chargeAmount = viewModel.chargeAmount

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Convert Currency",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SellBarComponent(
            sellingCurrency = selling.value,
            sellableCurrencies = sellable.value,
            sellAmount = sellAmount.value,
            onCurrencyChange = {
                selling.value = it
                viewModel.chargeRate = it.charge
                viewModel.loadOrFetchCurrencyRates(it.name)
            },
            onAmountChange = {
                viewModel.onSellAmountChange(it)
            },
            onTextFieldClick = { viewModel.resetInput() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        BuyBarComponent(
            buyingCurrencyRate = buying.value,
            buyableCurrencyRates = buyable.value,
            buyAmount = buyAmount.value,
            onSelectedCurrencyChange = {
                buying.value = it
                viewModel.conversionRate = it.currencyRate
                viewModel.resetInput()
            },
            onAmountChange = {
                viewModel.onBuyAmountChange(it)
            },
            onTextFieldClick = { viewModel.resetInput() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChargeBarComponent(
            chargeAmount = chargeAmount.value,
            sellingCurrency = selling.value,
        )
    }
}