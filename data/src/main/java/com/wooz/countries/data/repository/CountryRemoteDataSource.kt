package com.wooz.countries.data.repository

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import com.wooz.countries.data.framework.remote.common.ApiResponse

/**
 * @author wooz
 * @since 09/10/2020
 */
interface CountryRemoteDataSource {
    suspend fun getAllCountries(): ApiResponse<List<CountryDto>>
    suspend fun getCountryDetailsByCode(code: String): ApiResponse<CountryDetailsDto>
}