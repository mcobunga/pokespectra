package com.bonface.libs.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class StatDTO(
    @Json(name = "base_stat")
    val baseStat: Int,
    @Json(name = "effort")
    val effort: Int,
    @Json(name = "stat")
    val stat: StatDetails
)