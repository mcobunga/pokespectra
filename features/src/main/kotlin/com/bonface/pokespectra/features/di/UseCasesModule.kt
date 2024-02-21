package com.bonface.pokespectra.features.di

import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCaseImpl
import com.bonface.pokespectra.features.usecases.PokemonUseCase
import com.bonface.pokespectra.features.usecases.PokemonUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun providePokemonUseCase(
        useCase: PokemonUseCaseImpl
    ): PokemonUseCase

    @Binds
    abstract fun providePokemonDetailsUseCase(
        useCase: PokemonDetailsUseCaseImpl
    ): PokemonDetailsUseCase

}