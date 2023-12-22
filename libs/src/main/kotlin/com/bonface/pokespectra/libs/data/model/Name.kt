package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.bonface.pokespectra.libs.data.model.Language
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "language")
    val language: Language,
    @Json(name = "name")
    val name: String
)