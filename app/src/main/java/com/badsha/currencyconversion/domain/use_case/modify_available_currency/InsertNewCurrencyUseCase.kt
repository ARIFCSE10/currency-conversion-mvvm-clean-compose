package com.badsha.currencyconversion.domain.use_case.modify_available_currency

import com.badsha.currencyconversion.domain.model.Currency
import com.badsha.currencyconversion.domain.repository.CurrencyRepository

class InsertNewCurrencyUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        currency: Currency
    ) {
        return repository.insertCurrency(currency = currency)
    }
}