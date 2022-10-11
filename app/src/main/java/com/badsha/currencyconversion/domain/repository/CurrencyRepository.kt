package com.badsha.currencyconversion.domain.repository

import com.badsha.currencyconversion.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencies(): Flow<List<Currency>>
    suspend fun getCurrency(name: String): Flow<Currency?>
    suspend fun insertCurrency(currency: Currency)
    suspend fun updateCurrency(currency: Currency)
}