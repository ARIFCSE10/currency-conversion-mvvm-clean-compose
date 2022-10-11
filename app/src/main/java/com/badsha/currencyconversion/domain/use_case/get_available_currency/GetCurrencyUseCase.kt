package com.badsha.currencyconversion.domain.use_case.get_available_currency

import com.badsha.currencyconversion.domain.model.Currency
import com.badsha.currencyconversion.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetCurrencyUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        currencyName: String
    ): Flow<Currency?> {
        return repository.getCurrency(name = currencyName)
    }
}