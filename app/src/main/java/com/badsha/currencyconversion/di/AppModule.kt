package com.badsha.currencyconversion.di

import android.app.Application
import androidx.room.Room
import com.badsha.currencyconversion.common.Constants
import com.badsha.currencyconversion.data.local.CurrencyDB
import com.badsha.currencyconversion.data.remote.API
import com.badsha.currencyconversion.data.repository.CurrencyRepositoryImpl
import com.badsha.currencyconversion.data.repository.RateRepositoryImpl
import com.badsha.currencyconversion.domain.repository.CurrencyRepository
import com.badsha.currencyconversion.domain.repository.RateRepository
import com.badsha.currencyconversion.domain.use_case.CurrencyUseCases
import com.badsha.currencyconversion.domain.use_case.calculation.ChargeFreeOnTwoHundredSell
import com.badsha.currencyconversion.domain.use_case.get_available_currency.GetAvailableCurrenciesUseCase
import com.badsha.currencyconversion.domain.use_case.get_available_currency.GetAvailableCurrencyUseCase
import com.badsha.currencyconversion.domain.use_case.modify_available_currency.InsertNewCurrencyUseCase
import com.badsha.currencyconversion.domain.use_case.modify_available_currency.UpdateAvailableCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): API {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build().create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideRateRepository(api: API): RateRepository {
        return RateRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesCurrencyDB(app: Application): CurrencyDB {
        return Room.databaseBuilder(app, CurrencyDB::class.java, CurrencyDB.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun providesCurrencyRepository(currencyDB: CurrencyDB): CurrencyRepository {
        return CurrencyRepositoryImpl(currencyDB.currencyDao)
    }

    @Provides
    @Singleton
    fun providesCurrencyUseCases(currencyRepository: CurrencyRepository): CurrencyUseCases {
        return CurrencyUseCases(
            getAvailableCurrencyUseCase = GetAvailableCurrencyUseCase(currencyRepository),
            getAvailableCurrenciesUseCase = GetAvailableCurrenciesUseCase(currencyRepository),
            insertNewCurrencyUseCase = InsertNewCurrencyUseCase(currencyRepository),
            updateAvailableCurrencyUseCase = UpdateAvailableCurrencyUseCase(currencyRepository)
        )
    }

    @Provides
    @Singleton
    fun providesChargeCalculationUseCases(): ChargeFreeOnTwoHundredSell {
        return ChargeFreeOnTwoHundredSell()
    }


}