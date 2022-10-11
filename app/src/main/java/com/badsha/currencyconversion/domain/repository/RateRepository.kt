package com.badsha.currencyconversion.domain.repository

import com.badsha.currencyconversion.common.Constants
import com.badsha.currencyconversion.data.remote.dto.RateDto

interface RateRepository {
    suspend fun getRate(
        baseCurrency: String = Constants.BASE_CURRENCY,
        apiKey: String = Constants.API_KEY
    ): RateDto
}