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

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPokemonDetails(pokemonId: Int) {
        _uiState.value = UiState.Loading
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
                            is Resource.Error -> { _uiState.value = UiState.Error(result.message.toString()) }
                            is Resource.Success -> { _uiState.value = UiState.Success(result.data) }
                            else -> {}
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = UiState.Error(handleException(e).message.toString())
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

    sealed class UiState {
        data object Loading : UiState()
        data class Error(val message: String) : UiState()
        data class Success(val details: PokedexDetails?) : UiState()
    }

}