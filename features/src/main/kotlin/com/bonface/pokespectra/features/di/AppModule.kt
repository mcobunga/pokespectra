package com.bonface.pokespectra.features.di

import android.content.Context
import com.bonface.pokespectra.features.utils.ApiErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideErrorHandler(@ApplicationContext context: Context): ApiErrorHandler {
        return ApiErrorHandler(context)
    }

}