package com.badsha.currencyconversion.data.remote.dto

import com.badsha.currencyconversion.domain.model.Rate
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

fun RateDto.toRates(): List<Rate> {
    return rates.map {
        Rate(it.key, it.value)
    }
}