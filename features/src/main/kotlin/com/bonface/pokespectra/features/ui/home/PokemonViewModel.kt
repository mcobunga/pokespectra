package com.bonface.pokespectra.features.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.usecases.PokemonUseCase
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.di.IoDispatcher
import com.bonface.pokespectra.libs.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getPokemon()
    }

    fun getPokemon() {
        viewModelScope.launch(dispatcher) {
            pokemonUseCase.invoke().collect { result ->
                when(result) {
                    is Resource.Success -> _uiState.value = MainUiState.Success(result.data)
                    is Resource.Error -> _uiState.value = MainUiState.Error(result.message)
                    is Resource.Loading -> _uiState.value = MainUiState.Loading
                }
            }
        }
    }
    
}

sealed class MainUiState {
    data object Loading : MainUiState()
    data class Error(val message: String) : MainUiState()
    data class Success(val pokemon: PokemonResponse?) : MainUiState()
}
