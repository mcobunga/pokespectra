package com.bonface.pokespectra.features.ui.details

import com.bonface.pokespectra.libs.repository.PokemonRepository
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
    private lateinit var viewModel: PokemonDetailsViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    override fun beforeEach() {
        super.beforeEach()
        Dispatchers.setMain(Dispatchers.IO)
        viewModel = spyk(PokemonDetailsViewModel(pokemonRepository))
    }

    @After
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }


    @Test
    fun `should emit error object when api response error`() {
//        viewModel.viewState.collect {
//            viewStates.add(it)
//
//            val message = "Error from api"
//            coEvery {
//                pokemonRepository.getPokemon()
//            } throws IllegalAccessException(message)
//
//            viewModel.getPokemon()
//
//            coVerify {
//                pokemonRepository.getPokemon()
//            }
//            assertEquals(PokemonViewModel.ViewState.Error(message), viewModel.viewState.value)
//        }


    }





    @Test
    fun `Given that getPokemonDetails api call has been initiated, expose a loading ui state`() {
        viewModel = PokemonDetailsViewModel(pokemonRepository)
        // Assert
        assert(viewModel.viewState.value is PokemonDetailsViewModel.ViewState.Loading)
    }

    @Test
    fun `creating a viewmodel updates ui state to success after loading`() {
//        // Arrange
//        val viewModel = PokemonViewModel(pokemonRepository)
//
//        val expectedUiState = Resource.Success(testResponse)
//        // Assert
//        val actualState = viewModel.uiState.value
//        assertEquals(actualState, expectedUiState)
    }



    @Test
    fun `Given that getPokemon api call return success, make sure that we show a success state`() = runTest {

    }

    @Test
    fun `Given that getPokemon api call return an error, make sure that we show error message`() = runTest {

    }

}
