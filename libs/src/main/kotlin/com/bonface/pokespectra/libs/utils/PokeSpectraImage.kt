package com.bonface.pokespectra.libs.utils


fun getPokemonImageUrl(pokemonId: Int) = buildString {
    append("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/")
    append(pokemonId)
    append(".png")
}