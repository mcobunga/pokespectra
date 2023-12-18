package com.bonface.libs.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PokedexNumber(
    @Json(name = "entry_number")
    val entryNumber: Int,
    @Json(name = "pokedex")
    val pokedex: Pokedex
)