package com.bonface.pokespectra.features.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.utils.ErrorHandler
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.PokemonRepository
import com.bonface.pokespectra.libs.mappers.toPokedexDetails
import com.bonface.pokespectra.libs.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.model.PokedexDetails
import com.bonface.pokespectra.libs.model.PokemonSpeciesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<PokedexDetails>>(Resource.Loading())
    val uiState = _uiState.asStateFlow()

    fun getPokemonDetails(pokemonId: Int) {
        _uiState.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                flowOf(pokemonRepository.getPokemonDetails(pokemonId))
                    .zip(flowOf(pokemonRepository.getPokemonSpeciesDetails(pokemonId))) { pokemonDetails, pokemonSpecies ->
                        return@zip Pair<Response<DetailedPokedexResponse>, Response<PokemonSpeciesResponse>>(
                            pokemonDetails,
                            pokemonSpecies
                        )
                    }
                    .collect {
                        handleResponse(it)
                        _uiState.value = handleResponse(it)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                val error = ErrorHandler.handleException(e)
                _uiState.value = Resource.Error(error.message.toString())
            }
        }
    }

    private fun handleResponse(response: Pair<Response<DetailedPokedexResponse>, Response<PokemonSpeciesResponse>>): Resource<PokedexDetails> {
        if (response.first.isSuccessful && response.first.body() != null) {
            val detailsResult = response.first.body()
            if (response.second.isSuccessful && response.second.body() != null) {
                val speciesResult = response.second.body()
                val pairedResult = Pair(detailsResult!!, speciesResult!!)
                return Resource.Success(pairedResult.toPokedexDetails())
            }
            return Resource.Error(response.second.message().toString())
        }
        return Resource.Error(response.first.message().toString())
    }


}