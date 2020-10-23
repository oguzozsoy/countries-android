package com.wooz.countries.di

import com.wooz.countries.data.framework.local.CountryDao
import com.wooz.countries.data.framework.local.CountryLocalDataSourceImpl
import com.wooz.countries.data.framework.remote.CountryRemoteDataSourceImpl
import com.wooz.countries.data.framework.remote.CountryService
import com.wooz.countries.data.repository.CountryRemoteDataSource
import com.wooz.countries.data.repository.CountryRepositoryImpl
import com.wooz.countries.data.repository.CountryLocalDataSource
import com.wooz.countries.domain.repository.CountryRepository
import com.wooz.countries.mapper.CountryDetailsMapper
import com.wooz.countries.mapper.CountryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * @author wooz
 * @since 09/10/2020
 */
@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesCountryRepository(
        remoteDataSource: CountryRemoteDataSource,
        localDataSource: CountryLocalDataSource
    ): CountryRepository {
        return CountryRepositoryImpl(
            remoteDataSource,
            localDataSource,
            CountryMapper(),
            CountryDetailsMapper(),
        )
    }

    @Provides
    fun providesCountryRemoteDataSource(countryService: CountryService): CountryRemoteDataSource{
        return CountryRemoteDataSourceImpl(countryService)
    }

    @Provides
    fun providesCountryLocalDataSource(countryDao: CountryDao): CountryLocalDataSource {
        return CountryLocalDataSourceImpl(countryDao)
    }
}