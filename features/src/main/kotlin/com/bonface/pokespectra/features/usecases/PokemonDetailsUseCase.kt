package com.bonface.pokespectra.features.usecases

import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.domain.Resource
import com.bonface.pokespectra.libs.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(pokemonId: Int): Flow<Resource<DetailedPokedexResponse>> = repository.getPokemonDetails(pokemonId)

}