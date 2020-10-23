package com.wooz.countries.domain.entity

/**
 * @author wooz
 * @since 09/10/2020
 */
data class Country(
    val name: String = "",
    val code: String = "",
    val capital: String = "",
    val flag: String = "",
    var favorite: Boolean = false
)