package com.bonface.pokespectra.features.ui.home


import app.cash.turbine.test
import app.cash.turbine.testIn
import com.bonface.pokespectra.features.utils.DispatcherProvider
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.data.model.PokemonResponse
import com.bonface.pokespectra.libs.repository.PokemonRepository
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils
import com.bonface.pokespectra.utils.TestCreationUtils.getPokemon
import com.bonface.pokespectra.utils.TestDispatcherProvider
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonViewModelTest: BaseTest() {

    private val pokemonRepository: PokemonRepository = mockk(relaxed = true)
    private lateinit var viewModel: PokemonViewModel
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewStates: MutableList<PokemonViewModel.ViewState>

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    override fun beforeEach() {
        super.beforeEach()
        dispatcherProvider = TestDispatcherProvider()
        Dispatchers.setMain(dispatcherProvider.io)
        viewModel = PokemonViewModel(pokemonRepository)
    }

    @After
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }





    @Test
    fun `should emit error object when api response error`(): Unit = runBlocking{
        viewModel.viewState.collect {
            viewStates.add(it)

            val message = "Error from api"
            coEvery {
                pokemonRepository.getPokemon()
            } throws IllegalAccessException(message)

            viewModel.getPokemon()

            coVerify {
                pokemonRepository.getPokemon()
            }
            assertEquals(PokemonViewModel.ViewState.Error(message), viewModel.viewState.value)
        }


    }





    @Test
    fun `Given that getPokemon api call has been initiated, expose a loading ui state`() {
        viewModel = PokemonViewModel(pokemonRepository)
        // Assert
        assert(viewModel.viewState.value is PokemonViewModel.ViewState.Loading)
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


//    @Test
//    fun `Given that getPokemon api call returns error, make sure that we emit error state`() = runTest {
//        val message = "Something went wrong"
//        //Given
//        coEvery {
//            pokemonRepository.getPokemon()
//        } throws IllegalAccessException(message)
//        //When
//        viewModel.getPokemon()
//        coVerify {
//            pokemonRepository.getPokemon()
//        }
//        //Then
//        viewModel.uiState.test {
//            assert(awaitItem() is Resource.Loading)
//            assert(awaitItem() is Resource.Error)
//            assertEquals(Resource.Error(message), viewModel.uiState.value)
//        }
//
//    }
//



}
