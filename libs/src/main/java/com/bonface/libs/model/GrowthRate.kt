package com.bonface.libs.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class GrowthRate(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)