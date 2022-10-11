package com.badsha.currencyconversion.presentation.screen.home

import com.badsha.currencyconversion.domain.model.Currency

data class HomeState(
    val isLoading: Boolean = false,
    val currencies: List<Currency> = emptyList(),
    val error: Boolean = false,
    val errorMessage: String = ""
)