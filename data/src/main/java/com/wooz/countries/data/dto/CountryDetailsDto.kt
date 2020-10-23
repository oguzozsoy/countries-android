package com.wooz.countries.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wooz.countries.data.framework.local.common.CountryTypeConverters
import com.wooz.countries.domain.entity.Currency
import com.wooz.countries.domain.entity.Language

/**
 * @author wooz
 * @since 12/10/2020
 */
@Entity(primaryKeys = ["code"], tableName = "country_details")
@TypeConverters(CountryTypeConverters::class)
data class CountryDetailsDto(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String = "",

    @ColumnInfo(name = "code")
    @SerializedName("alpha2Code")
    val code: String = "",

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val capital: String = "",

    @ColumnInfo(name = "region")
    @SerializedName("region")
    val region: String = "",

    @ColumnInfo(name = "sub_region")
    @SerializedName("subregion")
    val subRegion: String = "",

    @ColumnInfo(name = "population")
    @SerializedName("population")
    val population: Int = -1,

    @ColumnInfo(name = "demonym")
    @SerializedName("demonym")
    val demonym: String = "",

    @ColumnInfo(name = "area")
    @SerializedName("area")
    val area: Int = -1,

    @ColumnInfo(name = "gini")
    @SerializedName("gini")
    val gini: Double = -1.0,

    @ColumnInfo(name = "native_name")
    @SerializedName("nativeName")
    val nativeName: String = "",

    @ColumnInfo(name = "numeric_code")
    @SerializedName("numericCode")
    val numericCode: String = "",

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val flag: String = "",

    @ColumnInfo(name = "calling_codes")
    @SerializedName("callingCodes")
    val callingCodes: List<String>,

    @ColumnInfo(name = "alt_spellings")
    @SerializedName("altSpellings")
    val altSpellings: List<String>,

    @ColumnInfo(name = "time_zones")
    @SerializedName("timezones")
    val timeZones: List<String>,

    @ColumnInfo(name = "borders")
    @SerializedName("borders")
    val borders: List<String>,

    @ColumnInfo(name = "lat_lng")
    @SerializedName("latlng")
    val latLng: List<Double>,

    @ColumnInfo(name = "languages")
    @SerializedName("languages")
    val languages: List<Language>,

    @ColumnInfo(name = "currencies")
    @SerializedName("currencies")
    val currencies: List<Currency>,

    @ColumnInfo(name = "updated_at")
    @Expose(serialize = false, deserialize = false)
    var updatedAt: Long = 0L
)