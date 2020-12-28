package com.wooz.countries.data.repository

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import kotlinx.coroutines.flow.Flow

/**
 * @author wooz
 * @since 10/10/2020
 */
interface CountryLocalDataSource {
    fun getAllCountries(): Flow<List<CountryDto>>
    suspend fun insertAllCountries(items: List<CountryDto>)
    fun getCountryDetailsByCode(code: String): Flow<CountryDetailsDto>
    suspend fun insertCountryDetails(country: CountryDetailsDto)
    fun getCountryByCode(code: String): Flow<CountryDto?>
    suspend fun updateCountry(country: CountryDto): Int
    suspend fun deleteCountry(country: CountryDto): Int
}