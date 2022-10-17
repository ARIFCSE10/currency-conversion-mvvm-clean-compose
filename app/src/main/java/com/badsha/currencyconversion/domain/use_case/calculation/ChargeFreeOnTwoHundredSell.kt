package com.badsha.currencyconversion.domain.use_case.calculation

import kotlin.math.min

class ChargeFreeOnTwoHundredSell {
    operator fun invoke(
        charge: Double = 0.0,
        sellAmount: Double = 0.0,
    ): Double {
        val offer = if (sellAmount >= 200) 0.0 else charge
        return min(offer, charge)
    }
}