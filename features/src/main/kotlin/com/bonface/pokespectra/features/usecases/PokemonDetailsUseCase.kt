package com.bonface.pokespectra.features.usecases

import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsUseCase {

    suspend operator fun invoke(pokemonId: Int): Flow<Resource<PokedexDetails>>

}