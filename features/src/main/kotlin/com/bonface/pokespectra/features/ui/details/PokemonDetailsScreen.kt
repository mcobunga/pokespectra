package com.bonface.pokespectra.features.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bonface.pokespectra.features.R
import com.bonface.pokespectra.features.ui.components.DetailScreenTopBar
import com.bonface.pokespectra.features.ui.components.ErrorOrEmpty
import com.bonface.pokespectra.features.ui.components.Loading
import com.bonface.pokespectra.features.ui.components.RetrySection
import com.bonface.pokespectra.features.utils.PokemonColorExt.getPokedexColor
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.features.utils.TopAppBarState
import com.bonface.pokespectra.features.utils.TopAppBarStateProvider
import com.bonface.pokespectra.libs.model.PokedexDetails
import java.util.Locale

@Composable
fun PokemonDetailsScreen(
    navController: NavHostController,
    pokemonId: Int,
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonDetails(pokemonId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(navController)
            when(uiState) {
                is Resource.Success -> {
                    uiState.data.let {
                        if (it != null) {
                            TopAppBarStateProvider.update(TopAppBarState(title = it.name.replaceFirstChar { it.titlecase() }))
                            PokemonDetails(it)
                        } else {
                            ErrorOrEmpty(errorMessage = stringResource(R.string.empty_pokemon))
                        }
                    }
                }
                is Resource.Error -> {
                    RetrySection(error = uiState.message.toString()) {
                        viewModel.getPokemonDetails(pokemonId)
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
fun TopBar(navController: NavHostController, title: String = stringResource(id = R.string.title_home)) {
    DetailScreenTopBar(onBackPress = { navController.navigateUp() } , title)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetails(pokedexDetails: PokedexDetails, modifier: Modifier = Modifier) {
    val pokedexColor = getPokedexColor(pokedexDetails.color)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = pokedexColor.color.copy(alpha = .5F))
    ) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .weight(.65F),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
            )

        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp),
            ) {

                Text(
                    text = "About",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = pokedexDetails.about,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = modifier.height(16.dp))

                Card(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp)

                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(text = "Weight: ")
                            Spacer(modifier = modifier.width(8.dp))
                            Text(
                                text = pokedexDetails.weight.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = modifier.height(8.dp))

                        Row(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Height: ")
                            Spacer(modifier = modifier.width(8.dp))
                            Text(
                                text = pokedexDetails.height.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = modifier.height(16.dp))

                        Text(text = "Abilities")
                        Spacer(modifier = modifier.height(8.dp))

                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            pokedexDetails.abilities.forEach { abilitiesPair ->
                                InputChip(
                                    selected = abilitiesPair.second,
                                    onClick = { },
                                    label = {
                                        Text(
                                            text = abilitiesPair.first,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    shape = RoundedCornerShape(percent = 80),
                                    colors = InputChipDefaults.inputChipColors(
                                        selectedContainerColor = pokedexColor
                                            .color
                                            .copy(alpha = .5F)
                                    )
                                )
                            }
                        }

                    }
                }

                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = "Stats",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = modifier.height(8.dp))
                pokedexDetails.stats.forEach { nameAndValue ->

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val statsValue = nameAndValue.second.toFloat() / 100
                        Text(
                            modifier = modifier.weight(.5F),
                            text = nameAndValue.first.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Box(
                            modifier = modifier.weight(.6F)
                        ) {
                            LinearProgressIndicator(
                                progress = statsValue,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(16.dp),
                                strokeCap = StrokeCap.Round,
                            )

                            Text(
                                text = nameAndValue.second.toString(),
                                modifier = modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 4.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                    }
                }

            }

        }
    }
}

@Preview
@Composable
fun PokemonDetailsPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        PokemonDetailsScreen(navController = rememberNavController(), 1)
    }
}