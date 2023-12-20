package com.bonface.pokespectra.libs.model

import androidx.annotation.Keep

@Keep
class MobileException(
    message: String,
    val statusCode: Int = -1
) : Exception(message)