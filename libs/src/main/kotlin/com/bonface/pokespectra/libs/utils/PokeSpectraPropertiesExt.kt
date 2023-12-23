package com.bonface.pokespectra.libs.utils


fun getPokemonImageUrl(pokemonId: Int) = buildString {
    append("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/")
    append(pokemonId)
    append(".png")
}

fun getPokemonHeight(height: Int) : String {
    //the api returned height is in decimetres, so we change it to metres
    return "${height / 10.0} m"
}

fun getPokemonWeight(weight: Int) : String {
    //the api returned weight is in hectograms, so we change it to kg
    return "${weight / 10.0} Kg"
}