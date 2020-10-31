package com.wooz.countries.domain.repository

import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.CountryDetails
import com.wooz.countries.domain.entity.ResultData
import kotlinx.coroutines.flow.Flow

/**
 * @author wooz
 * @since 09/10/2020
 */
interface CountryRepository {
    fun getAllCountries(): Flow<ResultData<List<Country>>>
    fun getCountryDetailsByCode(code: String): Flow<ResultData<CountryDetails>>
    fun getCountryByCode(code: String): Flow<ResultData<Country>>
    suspend fun updateCountry(country: Country): ResultData<Unit>
    suspend fun deleteCountry(country: Country): ResultData<Unit>
}