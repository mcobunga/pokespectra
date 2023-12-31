package com.bonface.pokespectra.libs.repository

import com.bonface.pokespectra.libs.data.api.PokemonApiService
import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApiService: PokemonApiService,
) {

    suspend fun getPokemon(): Response<PokemonResponse> = pokemonApiService.getPokemon()

    suspend fun getPokemonDetails(pokemonId: Int): Response<DetailedPokedexResponse> = pokemonApiService.getPokemonDetails(pokemonId)

    suspend fun getPokemonSpeciesDetails(pokemonId: Int): Response<PokemonSpeciesResponse> = pokemonApiService.getPokemonSpeciesDetails(pokemonId)

}