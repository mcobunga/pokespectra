package com.bonface.pokespectra.features.ui.details

import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.FakePokemonDetailsUseCase
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils.pokedexDetails
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

    @get: Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var fakeUseCase: FakePokemonDetailsUseCase

    @Before
    override fun setup() {
        super.setup()
        fakeUseCase = FakePokemonDetailsUseCase()
        viewModel = PokemonDetailsViewModel(fakeUseCase)
    }

    @After
    override fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `Given that viewmodel getPokemonDetails has been initiated, make sure that we show a loading state`() = runTest {
        fakeUseCase.emit(Resource.Loading())
        assert(viewModel.uiState.value is PokemonDetailsViewModel.DetailsUiState.Loading)
    }

    @Test
    fun `Given that getPokemonDetails api call return success, make sure that we show a success state`() = runTest {
        fakeUseCase.emit(Resource.Success(pokedexDetails()))
        assert(viewModel.uiState.value is PokemonDetailsViewModel.DetailsUiState.Success)
        assertNotNull((viewModel.uiState.value as PokemonDetailsViewModel.DetailsUiState.Success).details)
        assertEquals((viewModel.uiState.value as PokemonDetailsViewModel.DetailsUiState.Success).details?.pokemonId, pokedexDetails().pokemonId)
    }

    @Test
    fun `Given that getPokemon api call returns an error, make sure that we emit error state`() = runTest {
        fakeUseCase.emit(Resource.Error(message = "Something went wrong", data = null))
        assertNotNull((viewModel.uiState.value as PokemonDetailsViewModel.DetailsUiState.Error).message)
        assertEquals((viewModel.uiState.value as PokemonDetailsViewModel.DetailsUiState.Error).message, "Something went wrong")
    }

}
