package com.bonface.pokespectra.features.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.usecases.PokemonUseCase
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.di.NetworkModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
    @NetworkModule.IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val channel = Channel<Unit>(Channel.CONFLATED)

    init {
        getPokemon()
    }

//    fun getPokemon() {
//        channel.trySend(Unit)
//    }

    fun getPokemon() {
        viewModelScope.launch(dispatcher) {
            pokemonUseCase.fetch().collect { result ->
                when(result) {
                    is Resource.Success -> _uiState.value = MainUiState.Success(result.data)
                    is Resource.Error -> _uiState.value = MainUiState.Error(result.message.toString())
                    is Resource.Loading -> _uiState.value = MainUiState.Loading
                }
            }
        }
    }

    val mainUiState = channel
        .receiveAsFlow()
        .flatMapMerge {
            pokemonUseCase.fetch()
        }.map {
            when(it) {
                is Resource.Success -> MainUiState.Success(it.data)
                is Resource.Error -> MainUiState.Error(it.message.toString())
                is Resource.Loading -> MainUiState.Loading
            }
        }.flowOn(dispatcher)
        .stateIn(
            scope = viewModelScope,
            initialValue = MainUiState.Loading,
            started = SharingStarted.WhileSubscribed(5000L)
        )

}

sealed class MainUiState {
    data object Loading : MainUiState()
    data class Error(val message: String) : MainUiState()
    data class Success(val pokemon: PokemonResponse?) : MainUiState()
}