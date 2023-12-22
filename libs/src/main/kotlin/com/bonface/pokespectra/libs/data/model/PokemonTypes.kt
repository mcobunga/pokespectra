package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PokemonTypes(
    @Json(name = "slot")
    val slot: Int,
    @Json(name = "type")
    val pokemonType: Type
)