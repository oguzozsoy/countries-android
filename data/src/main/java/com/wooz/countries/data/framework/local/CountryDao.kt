package com.wooz.countries.data.framework.local

import androidx.room.*
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
    abstract suspend fun insertAllCountries(items: List<CountryDto>)

    @Query("select * from country")
    abstract fun getAllCountries(): Flow<List<CountryDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCountryDetailsWithTimeStamp(item: CountryDetailsDto)

    @Query("select * from country_details where code= :code")
    abstract fun getCountryDetailsByCode(code: String): Flow<CountryDetailsDto>

    suspend fun insertCountryDetails(item: CountryDetailsDto) {
        insertCountryDetailsWithTimeStamp(item.apply { updatedAt = System.currentTimeMillis() })
    }

    @Query("select * from country where code= :code")
    abstract fun getCountryByCode(code: String): Flow<CountryDto>

    @Update
    abstract suspend fun updateCountry(country: CountryDto): Int

    @Delete
    abstract suspend fun deleteCountry(country: CountryDto): Int
}