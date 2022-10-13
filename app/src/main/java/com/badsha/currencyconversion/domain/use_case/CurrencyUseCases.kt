package com.badsha.currencyconversion.domain.use_case

import com.badsha.currencyconversion.domain.use_case.get_available_currency.GetAvailableCurrenciesUseCase
import com.badsha.currencyconversion.domain.use_case.get_available_currency.GetAvailableCurrencyUseCase
import com.badsha.currencyconversion.domain.use_case.modify_available_currency.InsertNewCurrencyUseCase
import com.badsha.currencyconversion.domain.use_case.modify_available_currency.UpdateAvailableCurrencyUseCase

data class CurrencyUseCases(
    val getAvailableCurrencyUseCase: GetAvailableCurrencyUseCase,
    val getAvailableCurrenciesUseCase: GetAvailableCurrenciesUseCase,
    val insertNewCurrencyUseCase: InsertNewCurrencyUseCase,
    val updateAvailableCurrencyUseCase: UpdateAvailableCurrencyUseCase,
)