package com.wooz.countries.di

import com.wooz.countries.domain.repository.CountryRepository
import com.wooz.countries.domain.usercase.GetAllCountriesUseCase
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
object UseCaseModule {
    @Provides
    fun providesGetAllCountriesUseCase(repository: CountryRepository): GetAllCountriesUseCase {
        return GetAllCountriesUseCase(repository)
    }
}