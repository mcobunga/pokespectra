package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.bonface.pokespectra.libs.data.model.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Variety(
    @Json(name = "is_default")
    val isDefault: Boolean,
    @Json(name = "pokemon")
    val pokemon: Pokemon
)