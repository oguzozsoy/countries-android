package com.wooz.countries.domain.entity

/**
 * @author wooz
 * @since 12/10/2020
 */
data class CountryDetails(
    val name: String = "",
    val code: String = "",
    val capital: String = "",
    val region: String = "",
    val subRegion: String = "",
    val population: Int = -1,
    val demonym: String = "",
    val area: Int = -1,
    val gini: Double = -1.0,
    val nativeName: String = "",
    val numericCode: String = "",
    val flag: String = "",
    val callingCodes: List<String>,
    val altSpellings: List<String>,
    val timeZones: List<String>,
    val borders: List<String>,
    val latLng: List<Double>,
    val languages: List<Language>,
    val currencies: List<Currency>
)