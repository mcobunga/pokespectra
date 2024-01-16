package com.bonface.pokespectra.features.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.utils.ErrorHandler.handleException
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _mainUiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val viewState = _mainUiState.asStateFlow()

    init {
        getPokemon()
    }

    fun getPokemon() {
        _mainUiState.value = MainUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when(val result = handleResponse(pokemonRepository.getPokemon())) {
                    is Resource.Error -> { _mainUiState.value = MainUiState.Error(result.message.toString()) }
                    is Resource.Success -> { _mainUiState.value = MainUiState.Success(result.data) }
                    else -> {}
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _mainUiState.value = MainUiState.Error(handleException(e).message.toString())
            }
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

sealed class MainUiState {
    data object Loading : MainUiState()
    data class Error(val message: String) : MainUiState()
    data class Success(val pokemon: PokemonResponse?) : MainUiState()
}