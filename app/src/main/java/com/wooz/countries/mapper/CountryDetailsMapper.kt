package com.wooz.countries.mapper

import com.wooz.countries.data.dto.CountryDetailsDto
import com.wooz.countries.domain.common.Mapper
import com.wooz.countries.domain.entity.CountryDetails

/**
 * @author wooz
 * @since 09/10/2020
 */
class CountryDetailsMapper : Mapper<CountryDetails, CountryDetailsDto>() {
    override fun mapToDto(from: CountryDetails): CountryDetailsDto = CountryDetailsDto(
        name = from.name,
        code = from.code,
        capital = from.capital,
        region = from.region,
        subRegion = from.subRegion,
        population = from.population,
        demonym = from.demonym,
        area = from.area,
        gini = from.gini,
        nativeName = from.nativeName,
        numericCode = from.numericCode,
        flag = from.flag,
        callingCodes = from.callingCodes,
        altSpellings = from.altSpellings,
        timeZones = from.timeZones,
        borders = from.borders,
        latLng = from.latLng,
        languages = from.languages,
        currencies = from.currencies
    )

    override fun mapToEntity(from: CountryDetailsDto): CountryDetails = CountryDetails(
        name = from.name,
        code = from.code,
        capital = from.capital,
        region = from.region,
        subRegion = from.subRegion,
        population = from.population,
        demonym = from.demonym,
        area = from.area,
        gini = from.gini,
        nativeName = from.nativeName,
        numericCode = from.numericCode,
        flag = from.flag,
        callingCodes = from.callingCodes,
        altSpellings = from.altSpellings,
        timeZones = from.timeZones,
        borders = from.borders,
        latLng = from.latLng,
        languages = from.languages,
        currencies = from.currencies
    )
}