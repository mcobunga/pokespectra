package com.bonface.libs.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class OfficialArtwork(
    @Json(name = "front_default")
    val frontDefault: String,
    @Json(name = "front_shiny")
    val frontShiny: String
)