package com.bonface.pokespectra.utils

import com.bonface.pokespectra.libs.data.model.Ability
import com.bonface.pokespectra.libs.data.model.AbilityDetails
import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.FlavorTextEntry
import com.bonface.pokespectra.libs.data.model.Habitat
import com.bonface.pokespectra.libs.data.model.Language
import com.bonface.pokespectra.libs.data.model.Name
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.libs.data.model.Pokemon
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.data.model.PokemonTypes
import com.bonface.pokespectra.libs.data.model.Shape
import com.bonface.pokespectra.libs.data.model.Species
import com.bonface.pokespectra.libs.data.model.SpeciesColor
import com.bonface.pokespectra.libs.data.model.Sprites
import com.bonface.pokespectra.libs.data.model.Stat
import com.bonface.pokespectra.libs.data.model.StatDetails
import com.bonface.pokespectra.libs.data.model.Type
import com.bonface.pokespectra.libs.data.model.Variety
import com.bonface.pokespectra.libs.mappers.toPokedexDetails

object TestCreationUtils {

    fun getPokemon(): PokemonResponse {
        return PokemonResponse(
            count = 2,
            next = "https://pokeapi.co/api/v2/pokemon?offset=100&limit=100",
            previous = null,
            results = listOf(Pokemon(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"), Pokemon(name = "ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/"))
        )
    }

    private fun getPokemonDetails(): DetailedPokedexResponse {
        return DetailedPokedexResponse(
            abilities = listOf(Ability(ability = AbilityDetails(name= "overgrow", url = "https://pokeapi.co/api/v2/ability/65/"), isHidden = false, slot = 1)),
            baseExperience = 64,
            height = 7,
            id = 1,
            name = "bulbasaur",
            species = Species("bulbasaur", "https://pokeapi.co/api/v2/pokemon-species/1/"),
            sprites = Sprites(),
            stats = listOf(
                Stat(45, 0, StatDetails("hp", "https://pokeapi.co/api/v2/stat/1/")),
                Stat(49, 0, StatDetails("attack", "https://pokeapi.co/api/v2/stat/2/"))
            ),
            weight = 69,
            types = listOf(
                PokemonTypes(1, Type("grass", "https://pokeapi.co/api/v2/type/12/")),
                PokemonTypes(2, Type("poison", "https://pokeapi.co/api/v2/type/4/"))
            )
        )
    }

    private fun getPokemonSpecies(): PokemonSpeciesResponse {
        return PokemonSpeciesResponse(
            color = SpeciesColor("green", "https://pokeapi.co/api/v2/pokemon-color/5/"),
            flavorTextEntries = listOf(
                FlavorTextEntry(
                    "A strange seed was\nplanted on its\nback at birth.\nThe plant sprouts\nand grows with\nthis POKéMON.",
                    Language("en", "https://pokeapi.co/api/v2/language/9/")
                )
            ),
            habitat = Habitat("grassland", "https://pokeapi.co/api/v2/pokemon-habitat/3/"),
            id = 1,
            isBaby = false,
            name = "bulbasaur",
            names = listOf(Name(Language("roomaji", "https://pokeapi.co/api/v2/language/2/"), "Fushigidane")),
            shape = Shape("quadruped", "https://pokeapi.co/api/v2/pokemon-shape/8/"),
            varieties = listOf(
                Variety(true, Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
            )
        )
    }

    fun pokedexDetails(): PokedexDetails {
        val pairedResult = Pair(getPokemonDetails(), getPokemonSpecies())
        return pairedResult.toPokedexDetails()
    }

}



