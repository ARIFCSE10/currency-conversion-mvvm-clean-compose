package com.badsha.currencyconversion.domain.util

sealed class CurrencyOrderType {
    object Ascending : CurrencyOrderType()
    object Descending : CurrencyOrderType()
}