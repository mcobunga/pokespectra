package com.bonface.pokespectra.features.ui.details

import app.cash.turbine.test
import com.bonface.pokespectra.features.utils.DispatcherProvider
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.repository.PokemonRepository
import com.bonface.pokespectra.utils.BaseTest
import com.bonface.pokespectra.utils.MainDispatcherRule
import com.bonface.pokespectra.utils.TestDispatcherProvider
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonDetailsViewModelTest: BaseTest() {

    private val pokemonRepository: PokemonRepository = mockk(relaxed = true)
    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var dispatcherProvider: DispatcherProvider
    private val testResponse = ""

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    override fun beforeEach() {
        super.beforeEach()
        dispatcherProvider = TestDispatcherProvider()
        Dispatchers.setMain(dispatcherProvider.io)
        viewModel = spyk(PokemonDetailsViewModel(pokemonRepository))
    }

    @After
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }


}
