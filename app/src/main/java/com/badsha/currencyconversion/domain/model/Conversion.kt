package com.badsha.currencyconversion.domain.model

data class Conversion(
    val source: Currency,
    val destination: Currency,
    val sourceAmount: Double,
    val destinationAmount: Double,
    val charge: Double,
)
