package com.bonface.pokespectra.libs.api

import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PokemonApiServiceTest : MockPokemonApiServiceTest() {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    override fun before() {
        super.before()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    override fun after() {
        super.after()
        clearAllMocks()
    }

    @Test
    fun `should confirm fetching pokemon list from api source`() = runTest {
        val response = pokemonApiService.getPokemon()
        assertEquals(200, response.code())
        assertEquals(response.body()?.count, 2)
        assertEquals(response.body()?.results?.size, 2)
        assertEquals(response.body()?.results?.firstOrNull()?.name, "bulbasaur")
        assertEquals(response.body()?.results?.firstOrNull()?.url, "https://pokeapi.co/api/v2/pokemon/1/")
    }


    @Test
    fun `should confirm fetching selected pokemon details from api source`() = runTest {
        val response = pokemonApiService.getPokemonDetails(1)
        assertEquals(200, response.code())
        assertEquals(response.body()?.name, "bulbasaur")
        assertEquals(response.body()?.abilities?.firstOrNull()?.ability?.name, "overgrow")
        assertEquals(response.body()?.weight, 69)
        assertEquals(response.body()?.height, 7)
    }

    @Test
    fun `should confirm fetching selected pokemon species details from api source`() = runTest {
        val response = pokemonApiService.getPokemonSpeciesDetails(1)
        assertEquals(200, response.code())
        assertEquals(response.body()?.name, "bulbasaur")
        assertEquals(response.body()?.color?.name, "green")
        assertEquals(response.body()?.id, 1)
        assertEquals(response.body()?.habitat?.name, "grassland")
    }

}