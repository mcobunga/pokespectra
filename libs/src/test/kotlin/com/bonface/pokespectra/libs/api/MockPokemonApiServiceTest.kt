package com.bonface.pokespectra.libs.api

import com.bonface.pokespectra.libs.data.api.PokemonApiService
import com.bonface.pokespectra.libs.dispatcher.PokeSpectraDispatcher
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

open class MockPokemonApiServiceTest  {

    lateinit var pokemonApiService: PokemonApiService
    private lateinit var loggingInterceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var mockWebServer: MockWebServer


    @Before
    open fun before() {
        mockWebServer()
        setLoggingInterceptor()
        setRetrofitWithMoshi()
    }

    @After
    open fun after() {
        mockWebServer.shutdown()
    }

    private fun mockWebServer() {
        mockWebServer = MockWebServer().apply {
            dispatcher = PokeSpectraDispatcher()
            start()
        }
    }

    private fun setLoggingInterceptor() {
        loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = buildOkhttpClient(loggingInterceptor)
    }

    private fun setRetrofitWithMoshi() {
        pokemonApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PokemonApiService::class.java)
    }

    private fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private val moshi: Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

}