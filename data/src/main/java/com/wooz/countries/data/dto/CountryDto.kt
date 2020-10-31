package com.wooz.countries.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author wooz
 * @since 09/10/2020
 */
@Entity(primaryKeys = ["code"], tableName = "country")
data class CountryDto(

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String = "",

    @ColumnInfo(name = "code")
    @SerializedName("alpha2Code")
    val code: String = "",

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val capital: String = "",

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val flag: String = "",

    @ColumnInfo(name = "favorite")
    @Expose(serialize = false, deserialize = false)
    var favorite: Boolean = false
)
