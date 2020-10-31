package com.wooz.countries.di

import android.content.Context
import androidx.room.Room
import com.wooz.countries.data.framework.local.CountryDao
import com.wooz.countries.data.framework.local.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * @author wooz
 * @since 10/10/2020
 */
@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun providesCountryDatabase(@ApplicationContext context: Context): CountryDatabase {
        return Room
            .databaseBuilder(context, CountryDatabase::class.java, "country.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesCountryDao(countryDatabase: CountryDatabase): CountryDao {
        return countryDatabase.countryDao()
    }
}