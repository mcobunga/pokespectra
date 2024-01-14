package com.bonface.pokespectra.utils

import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import kotlinx.coroutines.flow.MutableSharedFlow

class FakePokemonDetailsUseCase: PokemonDetailsUseCase {
    private val fakeFlow = MutableSharedFlow<Resource<PokedexDetails>>()

    suspend fun emit(value: Resource<PokedexDetails>) = fakeFlow.emit(value)

    override fun fetch(pokemonId: Int) =  fakeFlow
}