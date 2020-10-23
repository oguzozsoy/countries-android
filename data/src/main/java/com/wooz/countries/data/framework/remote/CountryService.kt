package com.wooz.countries.data.framework.remote

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import com.wooz.countries.data.framework.remote.common.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author wooz
 * @since 09/10/2020
 */
interface CountryService {

    @GET("all?fields=name;capital;alpha2Code;flag")
    suspend fun getAllCountries(): ApiResponse<List<CountryDto>>

    @GET("alpha/{code}")
    suspend fun getCountryDetailsByCode(@Path("code") code: String): ApiResponse<CountryDetailsDto>
}