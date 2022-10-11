package com.badsha.currencyconversion.data.remote.dto

import com.badsha.currencyconversion.domain.model.Currency
import com.google.gson.annotations.SerializedName


data class RateDto(
    @SerializedName("base")
    val base: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("rates")
    val rates: Map<String, Double>,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("timestamp")
    val timestamp: Int?
)

fun RateDto.toCurrencies(): List<Currency> {
    return rates.map {
        Currency(name = it.key, rate = it.value)
    }
}