package com.wooz.countries.data.framework.local

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.data.dto.CountryDto
import com.wooz.countries.data.repository.CountryLocalDataSource
import com.wooz.countries.domain.entity.CountryDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author wooz
 * @since 10/10/2020
 */
class CountryLocalDataSourceImpl @Inject constructor(private val countryDao: CountryDao):
    CountryLocalDataSource {
    override suspend fun insertAllCountries(items: List<CountryDto>) {
        countryDao.insertAllCountries(items)
    }

    override fun getAllCountries(): Flow<List<CountryDto>> {
        return countryDao.getAllCountries()
    }

    override fun getCountryDetailsByCode(code: String): Flow<CountryDetailsDto> {
        return countryDao.getCountryDetailsByCode(code)
    }

    override suspend fun insertCountryDetails(country: CountryDetailsDto) {
        countryDao.insertCountryDetails(country)
    }
}