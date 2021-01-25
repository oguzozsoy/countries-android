package com.wooz.countries.di

import com.google.gson.GsonBuilder
import com.wooz.countries.data.framework.remote.CountryService
import com.wooz.countries.data.framework.remote.common.ApiResponseCallAdapterFactory
import com.wooz.countries.data.framework.remote.common.DeserializationExclusionStrategy
import com.wooz.countries.data.framework.remote.common.SerializationExclusionStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author wooz
 * @since 10/10/2020
 */
@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {
    @Provides
    fun providesBaseUrl(): String {
        return "https://restcountries.eu/rest/v2/"
    }

    @Provides
    fun providesCountryService(retrofit: Retrofit): CountryService {
        return retrofit.create(CountryService::class.java)
    }

    @Provides
    fun providesOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.callTimeout(10, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS)
        okHttpClient.readTimeout(10, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(logger)

        return okHttpClient.build()
    }

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun providesRetrofit(
        baseUrl: String,
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .addSerializationExclusionStrategy(SerializationExclusionStrategy())
                .addDeserializationExclusionStrategy(DeserializationExclusionStrategy())
                .create()
        )
    }
}