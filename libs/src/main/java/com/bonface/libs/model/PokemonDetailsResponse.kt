package com.bonface.libs.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PokemonDetailsResponse(
    @Json(name = "abilities")
    val abilities: List<Ability>,
    @Json(name = "base_experience")
    val baseExperience: Int,
    @Json(name = "forms")
    val forms: List<Form>,
    @Json(name = "height")
    val height: Int,
    @Json(name = "held_items")
    val heldItems: List<Any>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_default")
    val isDefault: Boolean,
    @Json(name = "location_area_encounters")
    val locationAreaEncounters: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "species")
    val species: SpeciesDTO,
    @Json(name = "sprites")
    val sprites: Sprites,
    @Json(name = "stats")
    val stats: List<StatDTO>,
    @Json(name = "weight")
    val weight: Int,
    @Json(name = "types")
    val types: List<PokemonTypes>
)