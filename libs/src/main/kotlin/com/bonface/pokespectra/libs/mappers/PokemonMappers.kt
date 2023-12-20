package com.bonface.pokespectra.libs.mappers

import com.bonface.pokespectra.libs.model.Pokedex
import com.bonface.pokespectra.libs.model.Pokemon
import com.bonface.pokespectra.libs.utils.getPokemonImageUrl

fun Pokemon.toPokedex(): Pokedex {
    val pokemonId = url.getPokemonIdFromUrl()
    return Pokedex(
        name = name,
        imageUrl = getPokemonImageUrl(pokemonId),
        pokemonId = pokemonId,
    )
}

fun String.getPokemonIdFromUrl(): Int {
    return split("/").toMutableList().apply {
        removeIf { value ->
            value.isBlank()
        }
    }.last().toInt()
}