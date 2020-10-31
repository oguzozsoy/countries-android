package com.wooz.countries.data.framework.local

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import com.wooz.countries.data.repository.CountryLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author wooz
 * @since 10/10/2020
 */
class CountryLocalDataSourceImpl @Inject constructor(private val countryDao: CountryDao) :
    CountryLocalDataSource {
    override suspend fun insertAllCountries(items: List<CountryDto>) {
        countryDao.insertAllCountries(items)
    }

    override fun getAllCountries(): Flow<List<CountryDto>> = countryDao.getAllCountries()

    override fun getCountryDetailsByCode(code: String): Flow<CountryDetailsDto> =
        countryDao.getCountryDetailsByCode(code)


    override suspend fun insertCountryDetails(country: CountryDetailsDto) {
        countryDao.insertCountryDetails(country)

    }

    override fun getCountryByCode(code: String): Flow<CountryDto?> =
        countryDao.getCountryByCode(code)

    override suspend fun updateCountry(country: CountryDto): Int = countryDao.updateCountry(country)
    override suspend fun deleteCountry(country: CountryDto): Int = countryDao.deleteCountry(country)

}