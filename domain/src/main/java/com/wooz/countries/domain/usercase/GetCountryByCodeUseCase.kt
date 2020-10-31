package com.wooz.countries.domain.usercase

import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

/**
 * @author wooz
 * @since 29/10/2020
 */
class GetCountryByCodeUseCase @Inject constructor(private val repository: CountryRepository) {
    operator fun invoke(code: String): Flow<ResultData<Country>> {
        return repository.getCountryByCode(code)
    }
}