package com.bonface.pokespectra.features.ui.home

import app.cash.turbine.test
import com.bonface.pokespectra.libs.repository.PokemonRepository
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils.getPokemon
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonViewModelTest: BaseTest() {

    private val pokemonRepository = mockk<PokemonRepository>(relaxed = true)
    private lateinit var pokemonViewModel: PokemonViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    override fun setup() {
        super.setup()
        pokemonViewModel = spyk(PokemonViewModel(pokemonRepository))
    }

    @After
    override fun teardown() {
        super.teardown()
        clearAllMocks()
    }


    @Test
    fun `Given that viewmodel has been initiated, make sure that we show a loading state`() {
        pokemonViewModel = PokemonViewModel(pokemonRepository)
        assert(pokemonViewModel.uiState.value is MainUiState.Loading)
    }

    @Test
    fun `Given that getPokemon api call return success, make sure that we show a success state`() = runTest {
        val result = getPokemon()
        coEvery {
            pokemonRepository.getPokemon().body()
        } returns result

        pokemonViewModel.getPokemon()
        coEvery {
            pokemonRepository.getPokemon()
        }
        assertEquals(result.results.size, 2)
    }


    @Test
    fun `Given that getPokemon api call returns error, make sure that we show an error message`() = runTest {
        //Given
        coEvery {
            pokemonRepository.getPokemon().message()
        } returns "Internal server error, try again later."
        //When
        pokemonViewModel.getPokemon()
        coVerify {
            pokemonRepository.getPokemon()
        }
        //Then
        pokemonViewModel.uiState.test {
            assert(awaitItem() is MainUiState.Error)
            assertEquals(MainUiState.Error("Internal server error, try again later."), pokemonViewModel.uiState.value)
        }
    }

}
