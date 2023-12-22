package com.bonface.pokespectra.features.ui.details

import app.cash.turbine.test
import com.bonface.pokespectra.libs.repository.PokemonRepository
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils
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
class PokemonDetailsViewModelTest: BaseTest() {

    private val pokemonRepository: PokemonRepository = mockk(relaxed = true)
    private lateinit var pokemonDetailsViewModel: PokemonDetailsViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    override fun setup() {
        super.setup()
        pokemonDetailsViewModel = spyk(PokemonDetailsViewModel(pokemonRepository))
    }

    @After
    override fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `Given that viewmodel has been initiated, make sure that we show a loading state`() {
        pokemonDetailsViewModel = PokemonDetailsViewModel(pokemonRepository)
        // Assert
        assert(pokemonDetailsViewModel.viewState.value is PokemonDetailsViewModel.ViewState.Loading)
    }

    @Test
    fun `Given that getPokemonDetails api call return success, make sure that we show a success state`() = runTest {
        val details = TestCreationUtils.getPokemonDetails()
        val species = TestCreationUtils.getPokemonSpecies()
        coEvery {
            pokemonRepository.getPokemonDetails(1).body()
        } returns details
        coEvery {
            pokemonRepository.getPokemonSpeciesDetails(1).body()
        } returns species

        pokemonDetailsViewModel.getPokemonDetails(1)

        coEvery {
            pokemonRepository.getPokemon()
            pokemonRepository.getPokemonSpeciesDetails(1).body()
        }
        assertEquals(details.name, "bulbasaur")
        assertEquals(details.weight, 69)
        assertEquals(species.color.name, "green")
        assertEquals(species.habitat.name, "grassland")
    }

    @Test
    fun `Given that getPokemonDetails api call returns error, make sure that we emit error state`() = runTest {
        //Given
        coEvery {
            pokemonRepository.getPokemonDetails(1)
            pokemonRepository.getPokemonSpeciesDetails(1)
        } throws RuntimeException("Something went wrong")
        //When
        pokemonDetailsViewModel.getPokemonDetails(1)
        coVerify {
            pokemonRepository.getPokemonDetails(1)
            pokemonRepository.getPokemonSpeciesDetails(1)
        }
        //Then
        pokemonDetailsViewModel.viewState.test {
            assert(awaitItem() is PokemonDetailsViewModel.ViewState.Error)
            assertEquals(PokemonDetailsViewModel.ViewState.Error("Something went wrong"), pokemonDetailsViewModel.viewState.value)
        }
    }

}
