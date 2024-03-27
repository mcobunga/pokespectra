package com.bonface.pokespectra.libs.repository

import com.bonface.pokespectra.libs.data.api.PokemonApiService
import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.domain.ApiErrorHandler
import com.bonface.pokespectra.libs.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    private val errorHandler: ApiErrorHandler
): PokemonRepository {
    override suspend fun getPokemon(): Flow<Resource<PokemonResponse>> = flow {
        try {
            emit(Resource.Loading())
            val result = pokemonApiService.getPokemon()
            emit(handlePokemonResponse(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(errorHandler.handleException(e).message.toString()))
        }
    }

    override suspend fun getPokemonDetails(pokemonId: Int): Flow<Resource<DetailedPokedexResponse>> = flow {
        try {
            emit(Resource.Loading())
            val result = pokemonApiService.getPokemonDetails(pokemonId)
            emit(handleDetailsResponse(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(errorHandler.handleException(e).message.toString()))
        }
    }

    override suspend fun getPokemonSpeciesDetails(pokemonId: Int): Flow<Resource<PokemonSpeciesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val result = pokemonApiService.getPokemonSpeciesDetails(pokemonId)
            emit(handlePokemonSpeciesResponse(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(errorHandler.handleException(e).message.toString()))
        }
    }

    private fun handlePokemonResponse(response: Response<PokemonResponse>): Resource<PokemonResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = response.message())
    }

    private  fun handleDetailsResponse(response: Response<DetailedPokedexResponse>): Resource<DetailedPokedexResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private  fun handlePokemonSpeciesResponse(response: Response<PokemonSpeciesResponse>): Resource<PokemonSpeciesResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


}