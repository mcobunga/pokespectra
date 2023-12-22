package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Pokedex(
    val name: String,
    val imageUrl: String,
    val pokemonId: Int,
)