package com.bonface.pokespectra.features.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.utils.ErrorHandler.handleException
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.mappers.toPokedexDetails
import com.bonface.pokespectra.libs.repository.PokemonRepository
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

    private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _detailsUiState.asStateFlow()

    fun getPokemonDetails(pokemonId: Int) {
        _detailsUiState.value = DetailsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                flowOf(pokemonRepository.getPokemonDetails(pokemonId))
                    .zip(flowOf(pokemonRepository.getPokemonSpeciesDetails(pokemonId))) { pokemonDetails, pokemonSpecies ->
                        return@zip Pair<Response<DetailedPokedexResponse>, Response<PokemonSpeciesResponse>>(
                            pokemonDetails,
                            pokemonSpecies
                        )
                    }.collect {
                        when(val result = handleResponse(it)) {
                            is Resource.Error -> { _detailsUiState.value = DetailsUiState.Error(result.message.toString()) }
                            is Resource.Success -> { _detailsUiState.value = DetailsUiState.Success(result.data) }
                            else -> {}
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                _detailsUiState.value = DetailsUiState.Error(handleException(e).message.toString())
            }
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
            return Resource.Error(response.second.message().toString())
        }
        return Resource.Error(response.first.message().toString())
    }

}

sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
    data class Success(val details: PokedexDetails?) : DetailsUiState()
}