package com.wooz.countries.data.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import kotlinx.coroutines.flow.Flow

/**
 * @author wooz
 * @since 10/10/2020
 */
@Dao
abstract class CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllCountriesWithTimeStamp(items: List<CountryDto>)

    @Query("select * from country")
    abstract fun getAllCountries(): Flow<List<CountryDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCountryDetailsWithTimeStamp(item: CountryDetailsDto)

    @Query("select * from country_details where code= :code")
    abstract fun getCountryDetailsByCode(code: String): Flow<CountryDetailsDto>

    suspend fun insertAllCountries(items: List<CountryDto>) {
        insertAllCountriesWithTimeStamp(items.map {
            it.updatedAt = System.currentTimeMillis()
            it
        })
    }

    suspend fun insertCountryDetails(item: CountryDetailsDto) {
        insertCountryDetailsWithTimeStamp(item.apply { updatedAt = System.currentTimeMillis() })
    }
}