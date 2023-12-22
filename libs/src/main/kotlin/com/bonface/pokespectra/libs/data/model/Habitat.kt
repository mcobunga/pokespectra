package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Habitat(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)