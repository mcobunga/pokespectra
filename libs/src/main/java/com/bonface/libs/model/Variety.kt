package com.bonface.libs.model

import androidx.annotation.Keep
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