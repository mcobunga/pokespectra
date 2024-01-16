package com.bonface.pokespectra.features.usecases

import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    fun fetch(): Flow<Resource<PokemonResponse>>

}