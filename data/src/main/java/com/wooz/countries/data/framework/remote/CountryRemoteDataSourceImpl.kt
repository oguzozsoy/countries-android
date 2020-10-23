package com.wooz.countries.data.framework.remote

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import com.wooz.countries.data.framework.remote.common.ApiResponse
import com.wooz.countries.data.repository.CountryRemoteDataSource
import javax.inject.Inject

/**
 * @author wooz
 * @since 09/10/2020
 */
class CountryRemoteDataSourceImpl @Inject constructor(private val countyService: CountryService):
    CountryRemoteDataSource {
    override suspend fun getAllCountries(): ApiResponse<List<CountryDto>> {
        return countyService.getAllCountries()
    }

    override suspend fun getCountryDetailsByCode(code: String): ApiResponse<CountryDetailsDto> {
        return countyService.getCountryDetailsByCode(code)
    }
}