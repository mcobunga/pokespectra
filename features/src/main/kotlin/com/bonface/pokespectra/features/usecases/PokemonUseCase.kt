package com.bonface.pokespectra.features.usecases

import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.domain.Resource
import com.bonface.pokespectra.libs.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(): Flow<Resource<PokemonResponse>> = repository.getPokemon()

}