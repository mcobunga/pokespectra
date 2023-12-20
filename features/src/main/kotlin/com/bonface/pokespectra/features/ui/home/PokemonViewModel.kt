package com.bonface.pokespectra.features.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonface.pokespectra.features.utils.ErrorHandler
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.PokemonRepository
import com.bonface.pokespectra.libs.model.PokemonResponse
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

    private val _pokemon = MutableStateFlow<Resource<PokemonResponse>>(Resource.Loading())
    val pokemon = _pokemon.asStateFlow()

    init {
        getPokemon()
    }

    fun getPokemon() {
        _pokemon.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = pokemonRepository.getPokemon()
                _pokemon.value = handleResponse(result)
            } catch (e: Exception) {
                e.printStackTrace()
                val error = ErrorHandler.handleException(e)
                _pokemon.value = Resource.Error(error.message.toString())
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