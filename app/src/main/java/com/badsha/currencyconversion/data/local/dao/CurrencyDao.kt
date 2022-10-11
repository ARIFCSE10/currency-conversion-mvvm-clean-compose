package com.badsha.currencyconversion.data.local.dao

import androidx.room.*
import com.badsha.currencyconversion.domain.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * from currency")
    fun getCurrencies(): Flow<List<Currency>>

    @Query("SELECT * from currency WHERE name = :name")
    suspend fun getCurrency(name: String): Flow<Currency?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Currency)

    @Update
    suspend fun updateCurrency(currency: Currency)
}