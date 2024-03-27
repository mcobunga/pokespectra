package com.bonface.pokespectra.features.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.usecases.PokemonSpeciesUseCase
import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.di.IoDispatcher
import com.bonface.pokespectra.libs.domain.Resource
import com.bonface.pokespectra.libs.mappers.toPokedexDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase,
    private val pokemonSpeciesUseCase: PokemonSpeciesUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    fun getPokemonDetails(pokemonId: Int) {
        viewModelScope.launch(dispatcher) {
            val (detailsRequest, speciesRequest) = listOf(
                async { pokemonDetailsUseCase.invoke(pokemonId) },
                async { pokemonSpeciesUseCase.invoke(pokemonId) },
            ).awaitAll()

            val detailsResult = detailsRequest as Flow<Resource<DetailedPokedexResponse>>
            val speciesResult = speciesRequest as Flow<Resource<PokemonSpeciesResponse>>

            detailsResult.collect { details ->
                when(details) {
                    is Resource.Success -> {
                        val pokemonDetails  = details.data
                        speciesResult.collect { species ->
                            when(species) {
                                is Resource.Success -> {
                                    val pokemonSpecies = species.data
                                    val result = Pair(pokemonDetails, pokemonSpecies).toPokedexDetails()
                                    _uiState.value = DetailsUiState.Success(result)
                                }
                                is Resource.Error -> _uiState.value = DetailsUiState.Error(species.message)
                                is Resource.Loading -> _uiState.value = DetailsUiState.Loading
                            }
                        }
                    }
                    is Resource.Error -> _uiState.value = DetailsUiState.Error(details.message)
                    is Resource.Loading -> _uiState.value = DetailsUiState.Loading
                }
            }
        }
    }

}

sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
    data class Success(val details: PokedexDetails?) : DetailsUiState()
}