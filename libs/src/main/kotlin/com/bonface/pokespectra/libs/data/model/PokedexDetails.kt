package com.bonface.pokespectra.libs.data.model

data class PokedexDetails(
    val pokemonId: Int,
    val name: String,
    val about: String,
    val weight: Int,
    val height: Int,
    val color: String,
    val imageUrl: String,
    val abilities: List<Pair<String, Boolean>>,
    val stats: List<Pair<String, Int>>,
    val types: List<String>,
)
