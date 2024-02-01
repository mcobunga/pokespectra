package com.bonface.pokespectra.features.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.libs.di.NetworkModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase,
    @NetworkModule.IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPokemonDetails(pokemonId: Int) {
        viewModelScope.launch(dispatcher) {
            pokemonDetailsUseCase.invoke(pokemonId).collect { result ->
                when(result) {
                    is Resource.Success -> _uiState.value = DetailsUiState.Success(result.data)
                    is Resource.Error -> _uiState.value = DetailsUiState.Error(result.message.toString())
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