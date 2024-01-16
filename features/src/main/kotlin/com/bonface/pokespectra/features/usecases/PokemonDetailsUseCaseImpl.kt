package com.bonface.pokespectra.features.usecases

import com.bonface.pokespectra.features.utils.ErrorHandler.handleException
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.mappers.toPokedexDetails
import com.bonface.pokespectra.libs.repository.PokemonRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import retrofit2.Response
import javax.inject.Inject

class PokemonDetailsUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
): PokemonDetailsUseCase {

    override fun fetch(pokemonId: Int) = flow {
        try {
            flowOf(repository.getPokemonDetails(pokemonId))
                .zip(flowOf(repository.getPokemonSpeciesDetails(pokemonId))) { pokemonDetails, pokemonSpecies ->
                    return@zip Pair<Response<DetailedPokedexResponse>, Response<PokemonSpeciesResponse>>(
                        pokemonDetails,
                        pokemonSpecies
                    )
                }.collect {
                    emit(handleResponse(it))
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(handleException(e).message.toString()))
        }
    }

    private  fun handleResponse(response: Pair<Response<DetailedPokedexResponse>, Response<PokemonSpeciesResponse>>): Resource<PokedexDetails> {
        if (response.first.isSuccessful && response.first.body() != null) {
            val detailsResult = response.first.body()
            if (response.second.isSuccessful && response.second.body() != null) {
                val speciesResult = response.second.body()
                val pairedResult = Pair(detailsResult!!, speciesResult!!)
                return Resource.Success(pairedResult.toPokedexDetails())
            }
            return Resource.Error(response.second.message())
        }
        return Resource.Error(response.first.message())
    }

}