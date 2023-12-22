package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Ability(
    @Json(name = "ability")
    val ability: AbilityDetails,
    @Json(name = "is_hidden")
    val isHidden: Boolean,
    @Json(name = "slot")
    val slot: Int
)