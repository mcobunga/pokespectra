package com.bonface.pokespectra.features.ui.details

import app.cash.turbine.test
import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.usecases.PokemonSpeciesUseCase
import com.bonface.pokespectra.libs.data.model.DetailedPokedexResponse
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.libs.data.model.PokemonSpeciesResponse
import com.bonface.pokespectra.libs.domain.Resource
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils
import com.bonface.pokespectra.utils.TestCreationUtils.getPokemonDetails
import com.bonface.pokespectra.utils.TestCreationUtils.getPokemonSpecies
import com.bonface.pokespectra.utils.TestCreationUtils.pokedexDetails
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.time.Duration
import java.time.OffsetDateTime

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonDetailsViewModelTest: BaseTest() {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: PokemonDetailsViewModel
    private val pokemonDetailsUseCase = mockk<PokemonDetailsUseCase>(relaxed = true)
    private val pokemonSpeciesUseCase = mockk<PokemonSpeciesUseCase>(relaxed = true)

    @Before
    override fun setup() {
        super.setup()
        viewModel = PokemonDetailsViewModel(pokemonDetailsUseCase, pokemonSpeciesUseCase, dispatcher)
    }

    @After
    override fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `Given that viewmodel getPokemonDetails has been initiated, make sure that we show a loading state`() = runTest(dispatcher) {
        val result = MutableStateFlow<Resource<DetailedPokedexResponse>>(Resource.Loading())
        // GIVEN
        coEvery {
            pokemonDetailsUseCase.invoke(1)
        } returns result
        // WHEN
        viewModel.getPokemonDetails(1)
        //THEN
        viewModel.uiState.test {
            assertEquals(DetailsUiState.Loading, awaitItem())
            assert(viewModel.uiState.value is DetailsUiState.Loading)
        }
    }

    @Test
    fun `Given that getPokemonDetails api call return success, make sure that we show a success state`() = runTest {
        val detailsResult = Resource.Success(getPokemonDetails())
        val speciesResult = Resource.Success(getPokemonSpecies())

        // GIVEN
        coEvery {
            pokemonDetailsUseCase.invoke(1)
        } returns flowOf(detailsResult)
        coEvery {
            pokemonSpeciesUseCase.invoke(1)
        } returns flowOf(speciesResult)
        // WHEN
        viewModel.getPokemonDetails(1)
        //THEN
        viewModel.uiState.test {
            assert(viewModel.uiState.value is DetailsUiState.Success)
            assertEquals(DetailsUiState.Success(pokedexDetails()), awaitItem())
            assertNotNull((viewModel.uiState.value as DetailsUiState.Success).details)
            assertEquals((viewModel.uiState.value as DetailsUiState.Success).details?.pokemonId, pokedexDetails().pokemonId)
        }
    }

    @Test
    fun `Given that getPokemon api call returns an error, make sure that we emit error state`() = runTest {
        val result = MutableStateFlow<Resource<DetailedPokedexResponse>>(Resource.Error(message = "Something went wrong"))
        // GIVEN
        coEvery {
            pokemonDetailsUseCase.invoke(1)
        } returns result
        // WHEN
        viewModel.getPokemonDetails(1)
        //THEN
        viewModel.uiState.test {
            assert(viewModel.uiState.value is DetailsUiState.Error)
            assertEquals(DetailsUiState.Error(message = "Something went wrong"), awaitItem())
            assertEquals((viewModel.uiState.value as DetailsUiState.Error).message, "Something went wrong")
        }
    }

}
