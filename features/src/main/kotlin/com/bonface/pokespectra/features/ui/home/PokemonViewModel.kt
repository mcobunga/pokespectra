package com.bonface.pokespectra.features.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.usecases.PokemonUseCase
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase
) : ViewModel() {

    private val channel = Channel<Unit>(Channel.CONFLATED)

    val uiState = channel
        .receiveAsFlow()
        .flatMapMerge {
            pokemonUseCase.fetch()
        }.map {
            when(it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.message.toString())
                is Resource.Loading -> UiState.Loading
            }
        }.flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            initialValue = UiState.Loading,
            started = SharingStarted.WhileSubscribed(5000L)
        )

    init {
        getPokemon()
    }

    fun getPokemon() {
        channel.trySend(Unit)
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Error(val message: String) : UiState()
        data class Success(val pokemon: PokemonResponse?) : UiState()
    }

}