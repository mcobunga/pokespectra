package com.bonface.pokespectra.features.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonface.pokespectra.core.fontFamily
import com.bonface.pokespectra.features.R
import com.bonface.pokespectra.features.ui.components.AppTopBar
import com.bonface.pokespectra.features.ui.components.ErrorOrEmpty
import com.bonface.pokespectra.features.ui.components.ImageFromURL
import com.bonface.pokespectra.features.ui.components.Loading
import com.bonface.pokespectra.features.ui.components.RetrySection
import com.bonface.pokespectra.libs.data.model.Pokedex
import com.bonface.pokespectra.libs.mappers.toPokedex
import com.bonface.pokespectra.libs.utils.getPokemonImageUrl

@Composable
fun MainScreen(
    navigateToPokemonDetails: (Int) -> Unit,
    pokemonViewModel: PokemonViewModel = hiltViewModel()
) {
    val uiState by pokemonViewModel.uiState.collectAsStateWithLifecycle()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppTopBar(stringResource(id = R.string.title_home))
            when(uiState) {
                is MainUiState.Success -> {
                    val result = (uiState as MainUiState.Success).pokemon?.results?.map {
                        it.toPokedex()
                    }
                    if (result != null) {
                        PokemonScreen(result, navigateToPokemonDetails)
                    } else {
                        ErrorOrEmpty(errorMessage = stringResource(R.string.empty_pokemon))
                    }
                }
                is MainUiState.Error -> {
                    val error = (uiState as MainUiState.Error).message
                    RetrySection(error = error) { pokemonViewModel.getPokemon() }
                }
                is MainUiState.Loading ->  Loading()
            }
        }
    }
}

@Composable
private fun PokemonScreen(pokedex: List<Pokedex>?, clickListener: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        val searchedText = textState.value.text
        SearchPokemon(state = textState, placeHolder = stringResource(R.string.search_placeholder))
        PokemonList(pokedex = pokedex, navigateToDetails = clickListener, searchedText)
    }
}

@Composable
private fun SearchPokemon(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    placeHolder: String
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp),
        shape = RoundedCornerShape(12.dp),
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(text = placeHolder, fontFamily = fontFamily)
        },
        enabled = true
    )
}

@Composable
private fun PokemonList(
    pokedex: List<Pokedex>?,
    navigateToDetails: (Int) -> Unit,
    searchedText: String
) {
    if (pokedex.isNullOrEmpty()) {
        ErrorOrEmpty(errorMessage = stringResource(R.string.empty_pokemon))
    } else {
        val filteredItems = if (searchedText.isNotEmpty()) {
            pokedex.filter {
                it.name.contains(searchedText, ignoreCase = true)
            }
        } else pokedex
        if (filteredItems.isEmpty()) {
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
                    items(filteredItems.size) { item ->
                        PokemonCard(navigateToDetails, filteredItems[item])
                    }
                }
            )
        }
    }
}

@Composable
private fun PokemonCard(
    navigateToDetails: (Int) -> Unit,
    pokedex: Pokedex,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable {
                navigateToDetails(pokedex.pokemonId)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Box(
            modifier = modifier
                .height(220.dp)
                .padding(top = 16.dp)
                .clickable(onClick = { navigateToDetails(pokedex.pokemonId) })
                .fillMaxSize(),
            Alignment.TopCenter
        ) {
            ImageFromURL(
                imageUrl = pokedex.imageUrl,
                size = 140.dp,
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 300f
                    )
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = pokedex.name.replaceFirstChar { it.titlecase() },
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode",
    showSystemUi = true
)
@Preview (uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
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
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            SearchPokemon(state = textState, placeHolder = stringResource(R.string.search_placeholder))
            PokemonList(pokedex = dummyPokedex, navigateToDetails = {}, searchedText = "")
        }
    }
}

private val dummyPokedex = List(100) {
    Pokedex("pokedex$it", getPokemonImageUrl(it + 1), it)
}
