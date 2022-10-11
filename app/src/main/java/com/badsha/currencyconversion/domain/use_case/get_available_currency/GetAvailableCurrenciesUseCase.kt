package com.badsha.currencyconversion.domain.use_case.get_available_currency

import com.badsha.currencyconversion.domain.model.Currency
import com.badsha.currencyconversion.domain.repository.CurrencyRepository
import com.badsha.currencyconversion.domain.util.CurrencyOrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAvailableCurrenciesUseCase(
    private val repository: CurrencyRepository
) {
    operator fun invoke(
        currencyOrderType: CurrencyOrderType = CurrencyOrderType.Descending
    ): Flow<List<Currency>> {
        return repository.getCurrencies().map { currencies ->
            when (currencyOrderType) {
                CurrencyOrderType.Ascending -> currencies.sortedBy { it.available }
                CurrencyOrderType.Descending -> currencies.sortedByDescending { it.available }
            }
        }
    }
}