package com.badsha.currencyconversion.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Currency(
    @PrimaryKey val name: String,
    val rate: Double,
    val available: Double = 0.0,
    val charge: Double = 0.7,
    val totalAmountConverted: Double = 0.0,
    val totalTimesConverted: Int = 0,
)
