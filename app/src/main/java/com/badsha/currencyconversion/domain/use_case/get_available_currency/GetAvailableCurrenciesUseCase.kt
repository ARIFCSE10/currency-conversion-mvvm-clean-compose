package com.badsha.currencyconversion.domain.use_case.get_available_currency

import com.badsha.currencyconversion.domain.model.Currency
import com.badsha.currencyconversion.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetAvailableCurrenciesUseCase(
    private val repository: CurrencyRepository
) {
    operator fun invoke(
//        currencyOrderType: CurrencyOrderType = CurrencyOrderType.Descending
    ): Flow<List<Currency>> {
        return repository.getCurrencies()
//            .map { currencies ->
//            when (currencyOrderType) {
//                CurrencyOrderType.Ascending -> currencies.sortedBy { it.available }
//                CurrencyOrderType.Descending -> currencies.sortedByDescending { it.available }
//            }
    }
}
