package com.bonface.pokespectra.features.ui.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bonface.pokespectra.core.PokeSpectraTheme
import com.bonface.pokespectra.core.lightScrim
import com.bonface.pokespectra.features.R
import com.bonface.pokespectra.features.ui.components.AppTopBar
import com.bonface.pokespectra.features.ui.components.ImageFromURL
import com.bonface.pokespectra.libs.model.Pokedex
import com.bonface.pokespectra.libs.utils.getPokemonImageUrl
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonface.pokespectra.features.ui.components.ErrorOrEmpty
import com.bonface.pokespectra.features.ui.components.Loading
import com.bonface.pokespectra.features.ui.components.RetrySection
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.libs.mappers.toPokedex

@Composable
fun MainScreen(
    navigateToPokemonDetails: (Int) -> Unit,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val pokemon by viewModel.pokemon.collectAsStateWithLifecycle()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppTopBar(stringResource(id = R.string.title_home))

            when(pokemon) {
                is Resource.Success -> {
                    val result = pokemon.data?.results?.map {
                        it.toPokedex()
                    }
                    PokemonScreen(result, navigateToPokemonDetails)
                }
                is Resource.Error -> {
                    RetrySection(error = pokemon.message.toString()) {
                        viewModel.getPokemon()
                    }
                }
                is Resource.Loading -> {
                    Loading()
                }
            }
        }
    }
}

@Composable
fun PokemonScreen(pokedex: List<Pokedex>?, clickListener: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchPokemon("", null, onSearchQueryChange = {})
        PokemonList(pokedex = pokedex, navigateToDetails = clickListener)
    }
}

@Composable
fun SearchPokemon(
    searchQuery: String,
    searchResults: List<Pokedex>?,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp),
        shape = RoundedCornerShape(12.dp),
        value = "",
        onValueChange = onSearchQueryChange,
        leadingIcon = {
            androidx.compose.material3.Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(text = stringResource(R.string.search_placeholder))
        },
        enabled = true
    )
}

@Composable
fun PokemonList(
    pokedex: List<Pokedex>?,
    modifier: Modifier = Modifier,
    navigateToDetails: (Int) -> Unit,
    scrollStateScreen: ScrollState = rememberScrollState()
) {
    if (pokedex.isNullOrEmpty()) {
        ErrorOrEmpty(errorMessage = stringResource(R.string.empty_pokemon))
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 10.dp,
                top = 16.dp,
                end = 10.dp,
                bottom = 16.dp
            ),
            state = rememberLazyGridState(),
            content = {
                items(pokedex.size) { index ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorScheme.surfaceVariant,
                        ),
                        modifier = modifier
                            .padding(6.dp)
                            .fillMaxWidth()
                            .clickable {
                                navigateToDetails(pokedex[index].pokemonId)
                           },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 3.dp
                        )
                    ) {
                        Column(
                            modifier = modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            Arrangement.SpaceEvenly
                        ) {
                            Box(
                                modifier = modifier
                                    .fillMaxWidth()
                            ) {
                                ImageFromURL(
                                    imageUrl = pokedex[index].imageUrl,
                                    size = 140.dp
                                )
                                Text(
                                    text = pokedex[index].name.replaceFirstChar { it.titlecase() },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = lightScrim,
                                    textAlign = TextAlign.Center,
                                    modifier = modifier
                                        .padding(16.dp)
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PokemonScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppTopBar(stringResource(id = R.string.title_home))
            SearchPokemon("", null, onSearchQueryChange = {})
            PokemonList(pokedex = dummyPokedex, navigateToDetails = {})
        }
    }
}

private val dummyPokedex = List(100) {
    Pokedex("pokedex$it", getPokemonImageUrl(it+1), it)
}
