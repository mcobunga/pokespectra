package com.bonface.pokespectra.features.di

import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.usecases.PokemonSpeciesUseCase
import com.bonface.pokespectra.features.usecases.PokemonUseCase
import com.bonface.pokespectra.libs.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providePokemonUseCase(
        repository: PokemonRepository
    ): PokemonUseCase {
        return PokemonUseCase(repository)
    }

    @Singleton
    @Provides
    fun providePokemonDetailsUseCase(
        repository: PokemonRepository
    ): PokemonDetailsUseCase {
        return PokemonDetailsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providePokemonSpeciesUseCase(
        repository: PokemonRepository
    ): PokemonSpeciesUseCase {
        return PokemonSpeciesUseCase(repository)
    }


}