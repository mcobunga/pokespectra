package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.bonface.pokespectra.libs.data.model.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String,
    @Json(name = "previous")
    val previous: String? = null,
    @Json(name = "results")
    val results: List<Pokemon>
)
