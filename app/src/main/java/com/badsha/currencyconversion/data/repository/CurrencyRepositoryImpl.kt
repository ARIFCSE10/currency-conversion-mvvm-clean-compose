package com.badsha.currencyconversion.data.repository

import com.badsha.currencyconversion.data.local.dao.CurrencyDao
import com.badsha.currencyconversion.domain.model.Currency
import com.badsha.currencyconversion.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class CurrencyRepositoryImpl(
    private val dao: CurrencyDao
) : CurrencyRepository {
    override fun getCurrencies(): Flow<List<Currency>> {
        return dao.getCurrencies()
    }

    override suspend fun getCurrency(name: String): Flow<Currency?> {
        return dao.getCurrency(name)
    }

    override suspend fun insertCurrency(currency: Currency) {
        dao.insertCurrency(currency)
    }

    override suspend fun updateCurrency(currency: Currency) {
        dao.updateCurrency(currency)
    }
}