package com.bonface.pokespectra.features.utils

object PokemonColorExt {
    fun getPokedexColor(color: String): PokedexColor {
        return when {
            color.contentEquals(PokedexColor.White.name, true) -> PokedexColor.White
            color.contentEquals(PokedexColor.Black.name, true) -> PokedexColor.Black
            color.contentEquals(PokedexColor.Blue.name, true) -> PokedexColor.Blue
            color.contentEquals(PokedexColor.Red.name, true) -> PokedexColor.Red
            color.contentEquals(PokedexColor.Green.name, true) -> PokedexColor.Green
            color.contentEquals(PokedexColor.Yellow.name, true) -> PokedexColor.Yellow
            color.contentEquals(PokedexColor.Gray.name, true) -> PokedexColor.Gray
            color.contentEquals(PokedexColor.Brown.name, true) -> PokedexColor.Brown
            color.contentEquals(PokedexColor.Pink.name, true) -> PokedexColor.Pink
            color.contentEquals(PokedexColor.Purple.name, true) -> PokedexColor.Purple
            else -> PokedexColor.NotSpecified
        }
    }
}