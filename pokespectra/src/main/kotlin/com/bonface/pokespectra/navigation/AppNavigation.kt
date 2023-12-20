package com.bonface.pokespectra.navigation

enum class Screen {
    HOME,
    DETAILS
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object PokemonDetails : NavigationItem(Screen.DETAILS.name)
}
