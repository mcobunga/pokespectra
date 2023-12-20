package com.bonface.pokespectra.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bonface.pokespectra.features.ui.details.PokemonDetailsScreen
import com.bonface.pokespectra.features.ui.home.MainScreen

@Composable
fun PokeSpectraNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) {
            MainScreen(navigateToPokemonDetails = { pokemonId ->
                navController.navigate("${NavigationItem.PokemonDetails.route}/$pokemonId")
            })
        }
        composable(
            route = NavigationItem.PokemonDetails.route + "/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(
                        500, easing = EaseOut
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val pokemonId = it.arguments?.getInt("pokemonId")
            if (pokemonId != null) {
                PokemonDetailsScreen(
                    navController = navController,
                    pokemonId = pokemonId
                )
            }
        }
    }
}
