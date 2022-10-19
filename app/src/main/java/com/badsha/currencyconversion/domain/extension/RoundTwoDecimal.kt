package com.badsha.currencyconversion.domain.extension

import kotlin.math.roundToInt

fun Double.roundTwoDeciaml(): Double {
    return (this * 100.0).roundToInt() / 100.00
}