package com.bonface.pokespectra.features.usecases

import com.bonface.pokespectra.features.utils.ErrorHandler.handleException
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.repository.PokemonRepository
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PokemonUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
): PokemonUseCase {

    override fun fetch() = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getPokemon()
            emit(handleResponse(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(handleException(e).message.toString()))
        }
    }

    private fun handleResponse(response: Response<PokemonResponse>): Resource<PokemonResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = response.message())
    }

}