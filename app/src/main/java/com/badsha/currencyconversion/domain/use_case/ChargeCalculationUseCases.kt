package com.badsha.currencyconversion.domain.use_case

import com.badsha.currencyconversion.domain.use_case.calculation.ChargeFreeForFirstFive
import com.badsha.currencyconversion.domain.use_case.calculation.ChargeFreeOnTwoHundredSell

data class ChargeCalculationUseCases(
    val chargeFreeOnTwoHundredSell: ChargeFreeOnTwoHundredSell,
    val chargeFreeForFirstFive: ChargeFreeForFirstFive,
)