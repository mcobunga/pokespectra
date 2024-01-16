package com.bonface.pokespectra.features.di

import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PokemonDetailsUseCaseModule {

    @Binds
    abstract fun providePokemonDetailsUseCase(
        useCase: PokemonDetailsUseCaseImpl
    ): PokemonDetailsUseCase
}