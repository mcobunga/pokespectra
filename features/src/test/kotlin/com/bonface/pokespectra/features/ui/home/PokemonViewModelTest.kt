package com.bonface.pokespectra.features.ui.home

import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.FakePokemonUseCase
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils.getPokemon
import   com.bonface.pokespectra.features.ui.home.PokemonViewModel
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

    @get: Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PokemonViewModel
    private lateinit var fakeUseCase: FakePokemonUseCase


    @Before
    override fun setup() {
        super.setup()
        fakeUseCase = FakePokemonUseCase()
        viewModel = PokemonViewModel(fakeUseCase)
    }

    @After
    override fun teardown() {
        clearAllMocks()
    }


    @Test
    fun `Given that viewmodel getPokemon has been initiated, make sure that we show a loading state`() = runTest {
        fakeUseCase.emit(Resource.Loading())
        assert(viewModel.uiState.value is PokemonViewModel.UiState.Loading)
    }

    @Test
    fun `Given that getPokemon api call return success, make sure that we show a success state`() = runTest {
        fakeUseCase.emit(Resource.Success(getPokemon()))
        assert(viewModel.uiState.value is PokemonViewModel.UiState.Success)
        assertNotNull((viewModel.uiState.value as PokemonViewModel.UiState.Success).pokemon)
        assertEquals((viewModel.uiState.value as PokemonViewModel.UiState.Success).pokemon?.count, getPokemon().count)
    }

    @Test
    fun `Given that getPokemon api call returns an error, make sure that we emit error state`() = runTest {
        fakeUseCase.emit(Resource.Error(message = "Something went wrong", data = null))
        assertNotNull((viewModel.uiState.value as PokemonViewModel.UiState.Error).message)
        assertEquals((viewModel.uiState.value as PokemonViewModel.UiState.Error).message, "Something went wrong")
    }

}
