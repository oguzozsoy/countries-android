package com.wooz.countries.data.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto

/**
 * @author wooz
 * @since 10/10/2020
 */
@Database(entities = [CountryDto::class, CountryDetailsDto::class], version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}