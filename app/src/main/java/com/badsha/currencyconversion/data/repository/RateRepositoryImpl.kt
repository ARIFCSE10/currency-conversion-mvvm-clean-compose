package com.badsha.currencyconversion.data.repository

import com.badsha.currencyconversion.data.remote.API
import com.badsha.currencyconversion.data.remote.dto.RateDto
import com.badsha.currencyconversion.domain.repository.RateRepository
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val api: API
) : RateRepository {
    override suspend fun getRate(baseCurrency: String, apiKey: String): RateDto {
        return api.getRate(baseCurrency = baseCurrency, apiKey = apiKey)
    }
}