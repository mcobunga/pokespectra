package com.bonface.pokespectra.libs.mappers

import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.Pokedex
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.libs.data.model.Pokemon
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.utils.getPokemonImageUrl

fun Pokemon.toPokedex(): Pokedex {
    val pokemonId = url.getPokemonIdFromUrl()
    return Pokedex(
        name = name,
        imageUrl = getPokemonImageUrl(pokemonId),
        pokemonId = pokemonId,
    )
}

fun Pair<DetailedPokedexResponse, PokemonSpeciesResponse>.toPokedexDetails(): PokedexDetails {
    val pokemonId = this.first.species.url.getPokemonIdFromUrl()
    return PokedexDetails(
        pokemonId = pokemonId,
        name = this.first.species.name,
        about = this.second.flavorTextEntries.firstOrNull()?.flavorText?.replace("\n", " ")
            .orEmpty(),
        weight = this.first.weight,
        height = this.first.height,
        color = this.second.color.name,
        imageUrl = getPokemonImageUrl(pokemonId),
        abilities = this.first.abilities.map {
            Pair(it.ability.name, it.isHidden)
        },
        stats = this.first.stats.map {
            Pair(it.stat.name, it.baseStat)
        },
        types = this.first.types.map {
            it.pokemonType.name
        }
    )
}

fun String.getPokemonIdFromUrl(): Int {
    return split("/").toMutableList().apply {
        removeIf { value ->
            value.isBlank()
        }
    }.last().toInt()
}