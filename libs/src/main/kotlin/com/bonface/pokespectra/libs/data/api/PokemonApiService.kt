package com.bonface.pokespectra.libs.data.api

import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): Response<PokemonResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): Response<DetailedPokedexResponse>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpeciesDetails(@Path("id") id: Int): Response<PokemonSpeciesResponse>
}