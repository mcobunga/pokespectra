package com.bonface.pokespectra.libs.data

import com.bonface.libs.api.PokemonApiService
import com.bonface.pokespectra.libs.model.Pokemon
import com.bonface.pokespectra.libs.model.PokemonResponse
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApiService: PokemonApiService,
) {

    suspend fun getPokemon(): Response<PokemonResponse> = pokemonApiService.getPokemon()

    suspend fun getPokemonDetails(pokemonId: Int): Response<Pokemon> = pokemonApiService.getPokemonDetails(pokemonId)

}