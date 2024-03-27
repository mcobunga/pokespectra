package com.bonface.pokespectra.libs.di

import com.bonface.pokespectra.libs.repository.PokemonRepository
import com.bonface.pokespectra.libs.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePokemonRepository(
        repository: PokemonRepositoryImpl
    ): PokemonRepository

}