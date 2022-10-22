package com.badsha.currencyconversion.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.badsha.currencyconversion.common.Constants


@Entity
data class Currency(
    @PrimaryKey val name: String,
    val rate: Double,
    val available: Double = 0.0,
    val charge: Double = 0.7,
    val totalAmountConverted: Double = 0.0,
    val totalTimesConverted: Int = 0,
) {
    companion object {
        val baseCurrencyPreloaded =
            Currency(name = Constants.BASE_CURRENCY, available = 1000.0, rate = 1.0, charge = 0.7)
    }
}