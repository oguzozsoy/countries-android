package com.wooz.countries.data.framework.local.common

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wooz.countries.domain.entity.Currency
import com.wooz.countries.domain.entity.Language

/**
 * @author wooz
 * @since 13/10/2020
 */
class CountryTypeConverters {
    @TypeConverter
    fun fromDoubleToString(value: Double): String?{
        return value.toString()
    }

    @TypeConverter
    fun fromStringToDouble(value: String): Double{
        return if(value.isEmpty()){
            0.0
        }else{
            value.toDouble()
        }
    }

    @TypeConverter
    fun fromStringToStringList(value: String?): List<String>? {
        return value?.let{
            it.split(",")
        }
    }

    @TypeConverter
    fun fromStringListToString(value: List<String>?): String? {
        return if(value.isNullOrEmpty()){
            ""
        }else{
            value?.joinToString(",")
        }
    }

    @TypeConverter
    fun stringToIntList(data: String?): List<Double>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toDouble()
                } catch (ex: NumberFormatException) {
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    fun intListToString(ints: List<Double>?): String? {
        return ints?.joinToString(",")
    }

    @TypeConverter
    fun languageListToString(languages: List<Language>?): String? {
        return Gson().toJson(languages)
    }

    @TypeConverter
    fun stringToLanguageList(data: String?): List<Language>? {
        return Gson().fromJson(data, object : TypeToken<List<Language>>(){}.type)
    }

    @TypeConverter
    fun currencyListToString(languages: List<Currency>?): String? {
        return Gson().toJson(languages)
    }

    @TypeConverter
    fun stringToCurrencyList(data: String?): List<Currency>? {
        return Gson().fromJson(data, object : TypeToken<List<Currency>>(){}.type)
    }

    companion object{
        private const val TAG = "CountryTypeConverters"
    }
}