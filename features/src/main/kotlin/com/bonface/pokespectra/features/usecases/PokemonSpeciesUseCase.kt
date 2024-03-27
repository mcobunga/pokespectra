package com.bonface.pokespectra.features.usecases

import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.domain.Resource
import com.bonface.pokespectra.libs.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonSpeciesUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int): Flow<Resource<PokemonSpeciesResponse>> = pokemonRepository.getPokemonSpeciesDetails(pokemonId)

}