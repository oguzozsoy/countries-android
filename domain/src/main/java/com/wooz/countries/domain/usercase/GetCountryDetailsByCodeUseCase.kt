package com.wooz.countries.domain.usercase

import com.wooz.countries.domain.entity.CountryDetails
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author wooz
 * @since 12/10/2020
 */
class GetCountryDetailsByCodeUseCase @Inject constructor(private val repository: CountryRepository) {
    operator fun invoke(code: String): Flow<ResultData<CountryDetails>> {
        return repository.getCountryDetailsByCode(code)
    }
}