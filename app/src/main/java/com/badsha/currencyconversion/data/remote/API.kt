package com.badsha.currencyconversion.data.remote

import com.badsha.currencyconversion.data.remote.dto.RateDto

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface API {
    @GET("latest")
    suspend fun getRate(
        @Header("apikey") apiKey: String,
        @Query("base") baseCurrency: String,
    ): RateDto
}