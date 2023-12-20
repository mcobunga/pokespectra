package com.bonface.pokespectra.features.ui.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bonface.pokespectra.features.ui.home.PokemonViewModel

@Composable
fun PokemonDetailsScreen(
    navController: NavHostController,
    pokemonId: Int,
    viewModel: PokemonViewModel = hiltViewModel()
) {

}