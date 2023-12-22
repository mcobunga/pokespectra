package com.bonface.pokespectra.libs.repository

import com.bonface.pokespectra.libs.api.PokemonApiServiceTest
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PokemonRepositoryTest : PokemonApiServiceTest() {

    @Mock
    private lateinit var pokemonRepository: PokemonRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    override fun before() {
        super.before()
        Dispatchers.setMain(testDispatcher)
        pokemonRepository = PokemonRepository(pokemonApiService)
    }

    @After
    override fun after() {
        super.after()
        clearAllMocks()
    }

    @Test
    fun `should confirm fetching pokemon list from api source`() = runTest {
        val response = pokemonRepository.getPokemon()
        assertEquals(200, response.code())
        assertEquals(response.body()?.count, 2)
        assertEquals(response.body()?.results?.size, 2)
        assertEquals(response.body()?.results?.firstOrNull()?.name, "bulbasaur")
        assertEquals(response.body()?.results?.firstOrNull()?.url, "https://pokeapi.co/api/v2/pokemon/1/")
    }


    @Test
    fun `should confirm fetching selected pokemon details from api source`() = runTest {
        val response = pokemonRepository.getPokemonDetails(1)
        assertEquals(200, response.code())
        assertEquals(response.body()?.name, "bulbasaur")
        assertEquals(response.body()?.abilities?.firstOrNull()?.ability?.name, "overgrow")
        assertEquals(response.body()?.weight, 69)
        assertEquals(response.body()?.height, 7)
    }

    @Test
    fun `should confirm fetching selected pokemon species details from api source`() = runTest {
        val response = pokemonRepository.getPokemonSpeciesDetails(1)
        assertEquals(200, response.code())
        assertEquals(response.body()?.name, "bulbasaur")
        assertEquals(response.body()?.color?.name, "green")
        assertEquals(response.body()?.id, 1)
        assertEquals(response.body()?.habitat?.name, "grassland")
    }

}