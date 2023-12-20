package com.bonface.libs.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class SpeciesResponse(
    @Json(name = "base_happiness")
    val baseHappiness: Int,
    @Json(name = "capture_rate")
    val captureRate: Int,
    @Json(name = "color")
    val color: SpeciesColor,
    @Json(name = "evolution_chain")
    val evolutionChain: EvolutionChain,
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @Json(name = "forms_switchable")
    val formsSwitchable: Boolean,
    @Json(name = "gender_rate")
    val genderRate: Int,
    @Json(name = "generation")
    val generation: Generation,
    @Json(name = "growth_rate")
    val growthRate: GrowthRate,
    @Json(name = "habitat")
    val habitat: Habitat,
    @Json(name = "has_gender_differences")
    val hasGenderDifferences: Boolean,
    @Json(name = "hatch_counter")
    val hatchCounter: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_baby")
    val isBaby: Boolean,
    @Json(name = "is_legendary")
    val isLegendary: Boolean,
    @Json(name = "is_mythical")
    val isMythical: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "names")
    val names: List<Name>,
    @Json(name = "order")
    val order: Int,
    @Json(name = "pal_park_encounters")
    val palParkEncounters: List<PalParkEncounter>,
    @Json(name = "pokedex_numbers")
    val pokedexNumbers: List<PokedexNumber>,
    @Json(name = "shape")
    val shape: Shape,
    @Json(name = "varieties")
    val varieties: List<Variety>
)