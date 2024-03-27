package com.bonface.pokespectra.libs.repository

import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.domain.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PokemonRepository{

    suspend fun getPokemon(): Flow<Resource<PokemonResponse>>

    suspend fun getPokemonDetails(pokemonId: Int): Flow<Resource<DetailedPokedexResponse>>

    suspend fun getPokemonSpeciesDetails(pokemonId: Int): Flow<Resource<PokemonSpeciesResponse>>

}