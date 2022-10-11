package com.badsha.currencyconversion.di

import com.badsha.currencyconversion.common.Constants
import com.badsha.currencyconversion.data.remote.API
import com.badsha.currencyconversion.data.repository.RateRepositoryImpl
import com.badsha.currencyconversion.domain.repository.RateRepository
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

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideConversionRepository(api: API): RateRepository {
        return RateRepositoryImpl(api)
    }
}