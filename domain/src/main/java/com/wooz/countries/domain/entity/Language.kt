package com.wooz.countries.domain.entity

/**
 * @author wooz
 * @since 13/10/2020
 */
data class Language (
    val iso639_1: String,
    val iso639_2: String,
    val name: String,
    val nativeName: String
)