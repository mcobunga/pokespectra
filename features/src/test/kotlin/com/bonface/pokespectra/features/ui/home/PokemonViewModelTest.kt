package com.bonface.pokespectra.features.ui.home

import app.cash.turbine.test
import com.bonface.pokespectra.features.usecases.PokemonUseCase
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils.getPokemon
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
class PokemonViewModelTest: BaseTest() {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: PokemonViewModel
    private val useCase = mockk<PokemonUseCase>(relaxed = true)

    @Before
    override fun setup() {
        super.setup()
        viewModel = PokemonViewModel(useCase, dispatcher)
    }

    @After
    override fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `Given that viewmodel getPokemon has been initiated, make sure that we show a loading state`() = runTest {
        val result = MutableStateFlow<Resource<PokemonResponse>>(Resource.Loading())
        // GIVEN
        coEvery {
            useCase.fetch()
        } returns result
        // WHEN
        viewModel.getPokemon()
        //THEN
        viewModel.uiState.test {
            assertEquals(MainUiState.Loading, awaitItem())
            assert(viewModel.uiState.value is MainUiState.Loading)
        }
    }

    @Test
    fun `Given that getPokemon api call return success, make sure that we show a success state`() = runTest {
        val result = MutableStateFlow<Resource<PokemonResponse>>(Resource.Success(getPokemon()))
        // GIVEN
        coEvery {
            useCase.fetch()
        } returns result
        // WHEN
        viewModel.getPokemon()
        //THEN
        viewModel.uiState.test {
            assert(viewModel.uiState.value is MainUiState.Success)
            assertEquals(MainUiState.Success(getPokemon()), awaitItem())
            assertNotNull((viewModel.uiState.value as MainUiState.Success).pokemon)
            assertEquals((viewModel.uiState.value as MainUiState.Success).pokemon?.count, getPokemon().count)
        }
    }

    @Test
    fun `Given that getPokemon api call returns an error, make sure that we emit error state`() = runTest {
        val result = MutableStateFlow<Resource<PokemonResponse>>(Resource.Error(message = "Something went wrong", data = null))
        // GIVEN
        coEvery {
            useCase.fetch()
        } returns result
        // WHEN
        viewModel.getPokemon()
        //THEN
        viewModel.uiState.test {
            assert(viewModel.uiState.value is MainUiState.Error)
            assertEquals(MainUiState.Error(message = "Something went wrong"), awaitItem())
            assertEquals((viewModel.uiState.value as MainUiState.Error).message, "Something went wrong")
        }
    }

}
