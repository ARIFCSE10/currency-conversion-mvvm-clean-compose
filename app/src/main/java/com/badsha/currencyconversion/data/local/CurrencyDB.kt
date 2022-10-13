package com.badsha.currencyconversion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.badsha.currencyconversion.data.local.dao.CurrencyDao
import com.badsha.currencyconversion.domain.model.Currency


@Database(
    entities = [Currency::class],
    version = 1,
    exportSchema = false,
)
abstract class CurrencyDB : RoomDatabase() {
    abstract val currencyDao: CurrencyDao

    companion object {
        const val DATABASE_NAME = "currency_db"
    }
}