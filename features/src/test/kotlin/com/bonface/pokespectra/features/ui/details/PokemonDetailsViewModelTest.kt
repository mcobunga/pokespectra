package com.bonface.pokespectra.features.ui.details

import app.cash.turbine.test
import com.bonface.pokespectra.features.usecases.PokemonDetailsUseCase
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokedexDetails
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils.pokedexDetails
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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

    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: PokemonDetailsViewModel
    private val useCase = mockk<PokemonDetailsUseCase>(relaxed = true)

    @Before
    override fun setup() {
        super.setup()
        viewModel = PokemonDetailsViewModel(useCase, dispatcher)
    }

    @After
    override fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `Given that viewmodel getPokemonDetails has been initiated, make sure that we show a loading state`() = runTest(dispatcher) {
        val result = MutableStateFlow<Resource<PokedexDetails>>(Resource.Loading())
        // GIVEN
        coEvery {
            useCase.fetch(1)
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
        val result = MutableStateFlow<Resource<PokedexDetails>>(Resource.Success(pokedexDetails()))
        // GIVEN
        coEvery {
            useCase.fetch(1)
        } returns result
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
        val result = MutableStateFlow<Resource<PokedexDetails>>(Resource.Error(message = "Something went wrong", data = null))
        // GIVEN
        coEvery {
            useCase.fetch(1)
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
