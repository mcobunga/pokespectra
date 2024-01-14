package com.bonface.pokespectra.utils

import com.bonface.pokespectra.features.usecases.PokemonUseCase
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import kotlinx.coroutines.flow.MutableSharedFlow

class FakePokemonUseCase: PokemonUseCase {

    private val fakeFlow = MutableSharedFlow<Resource<PokemonResponse>>()

    suspend fun emit(value: Resource<PokemonResponse>) = fakeFlow.emit(value)

    override fun fetch() =  fakeFlow
}