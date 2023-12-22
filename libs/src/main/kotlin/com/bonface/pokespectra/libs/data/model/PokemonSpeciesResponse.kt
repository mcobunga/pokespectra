package com.bonface.pokespectra.libs.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    @Json(name = "color")
    val color: SpeciesColor,
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @Json(name = "habitat")
    val habitat: Habitat,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_baby")
    val isBaby: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "names")
    val names: List<Name>,
    @Json(name = "shape")
    val shape: Shape,
    @Json(name = "varieties")
    val varieties: List<Variety>
)