package com.badsha.currencyconversion.domain.use_case.calculation

import com.badsha.currencyconversion.domain.model.Currency
import kotlin.math.min

class ChargeFreeForFirstFive {
    operator fun invoke(
        charge: Double = 0.0,
        sellingCurrency: Currency,
    ): Double {
        val offer = if (sellingCurrency.totalTimesConverted < 5) 0.0 else charge
        return min(offer, charge)
    }
}