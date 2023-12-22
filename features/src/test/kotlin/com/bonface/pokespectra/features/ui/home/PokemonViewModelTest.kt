package com.bonface.pokespectra.features.ui.home


import app.cash.turbine.test
import com.bonface.pokespectra.libs.repository.PokemonRepository
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestCreationUtils.getPokemon
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
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonViewModelTest: BaseTest() {

    private val pokemonRepository: PokemonRepository = mockk(relaxed = true)
    private lateinit var viewModel: PokemonViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    override fun beforeEach() {
        super.beforeEach()
        Dispatchers.setMain(Dispatchers.IO)
        viewModel = PokemonViewModel(pokemonRepository)
    }

    @After
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }


    @Test
    fun `Given that viewmodel has been initiated, make sure that we show a loading state`() {
        viewModel = PokemonViewModel(pokemonRepository)
        // Assert
        assert(viewModel.viewState.value is PokemonViewModel.ViewState.Loading)
    }

    @Test
    fun `Given that getPokemon api call return success, make sure that we show a success state`() = runTest {
        val result = getPokemon()
        coEvery {
            pokemonRepository.getPokemon().body()
        } returns result

        viewModel.getPokemon()
        coEvery {
            pokemonRepository.getPokemon()
        }
        assertEquals(result.results.size, 2)
    }


    @Test
    fun `Given that getPokemon api call returns error, make sure that we show error message`() = runTest {
        //Given
        coEvery {
            pokemonRepository.getPokemon()
        } throws Exception("Something went wrong")
        //When
        viewModel.getPokemon()
        coVerify {
            pokemonRepository.getPokemon()
        }
        //Then
        viewModel.viewState.test {
            assert(awaitItem() is PokemonViewModel.ViewState.Error)
            assertEquals(PokemonViewModel.ViewState.Error("Something went wrong"), viewModel.viewState.value)
        }
    }

}
