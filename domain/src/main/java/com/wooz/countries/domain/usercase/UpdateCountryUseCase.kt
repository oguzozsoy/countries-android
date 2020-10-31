package com.wooz.countries.domain.usercase

import com.wooz.countries.domain.entity.Country
import com.wooz.countries.domain.entity.ResultData
import com.wooz.countries.domain.repository.CountryRepository
import javax.inject.Inject

/**
 * @author wooz
 * @since 29/10/2020
 */
class UpdateCountryUseCase @Inject constructor(private val repository: CountryRepository) {
    suspend operator fun invoke(country: Country): ResultData<Unit>{
        return repository.updateCountry(country)
    }
}