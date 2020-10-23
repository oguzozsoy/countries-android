package com.wooz.countries.domain.usercase

import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author wooz
 * @since 09/10/2020
 */
class GetAllCountriesUseCase @Inject constructor(private val repository: CountryRepository) {
    operator fun invoke(): Flow<ResultData<List<Country>>> {
        return repository.getAllCountries()
    }
}