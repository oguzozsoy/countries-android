package com.wooz.countries.mapper

import com.wooz.countries.data.dto.CountryDto
import com.wooz.countries.domain.common.Mapper
import com.wooz.countries.domain.entity.Country

/**
 * @author wooz
 * @since 09/10/2020
 */
class CountryMapper : Mapper<Country, CountryDto>() {
    override fun mapToDto(from: Country): CountryDto = CountryDto(
        name = from.name,
        code = from.code,
        capital = from.capital,
        flag = from.flag,
        favorite = from.favorite
    )

    override fun mapToEntity(from: CountryDto): Country = Country(
        name = from.name,
        code = from.code,
        capital = from.capital,
        flag = from.flag,
        favorite = from.favorite
    )
}