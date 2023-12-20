package com.bonface.libs.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PalParkEncounter(
    @Json(name = "area")
    val area: Area,
    @Json(name = "base_score")
    val baseScore: Int,
    @Json(name = "rate")
    val rate: Int
)