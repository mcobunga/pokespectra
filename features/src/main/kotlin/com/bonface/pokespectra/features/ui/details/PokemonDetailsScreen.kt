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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bonface.pokespectra.core.lightOnPrimary
import com.bonface.pokespectra.core.lightOutline
import com.bonface.pokespectra.core.lightOutlineVariant
import com.bonface.pokespectra.core.lightScrim
import com.bonface.pokespectra.features.R
import com.bonface.pokespectra.features.ui.components.DetailScreenTopBar
import com.bonface.pokespectra.features.ui.components.ErrorOrEmpty
import com.bonface.pokespectra.features.ui.components.ImageFromURL
import com.bonface.pokespectra.features.ui.components.Loading
import com.bonface.pokespectra.features.ui.components.RetrySection
import com.bonface.pokespectra.features.utils.PokedexColor
import com.bonface.pokespectra.features.utils.PokemonColorExt.getPokedexColor
import com.bonface.pokespectra.features.utils.Resource
import com.bonface.pokespectra.features.utils.TopAppBarState
import com.bonface.pokespectra.features.utils.TopAppBarStateProvider
import com.bonface.pokespectra.libs.model.PokedexDetails

@Composable
fun PokemonDetailsScreen(
    navController: NavHostController,
    pokemonId: Int,
    pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val uiState by pokemonDetailsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        pokemonDetailsViewModel.getPokemonDetails(pokemonId)
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
                    uiState.data.let { result ->
                        if (result != null) {
                            TopAppBarStateProvider.update(TopAppBarState(title = result.name.replaceFirstChar { it.titlecase() }, color = getPokedexColor(result.color).color.copy(alpha = .5F)))
                            PokemonDetails(result)
                        } else {
                            ErrorOrEmpty(errorMessage = stringResource(R.string.empty_pokemon))
                        }
                    }
                }
                is Resource.Error -> {
                    RetrySection(error = uiState.message.toString()) {
                        pokemonDetailsViewModel.getPokemonDetails(pokemonId)
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
fun TopBar(navController: NavHostController) {
    DetailScreenTopBar { navController.navigateUp() }
}

@Composable
fun PokemonDetails(pokedexDetails: PokedexDetails, modifier: Modifier = Modifier) {
    val pokedexColor = getPokedexColor(pokedexDetails.color)
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            PokemonImage(pokedexDetails.imageUrl, pokedexColor)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            ) {
                PokemonAbout(pokedexDetails.name, pokedexDetails.about)
                PokemonAbilities(pokedexDetails.name, pokedexDetails.abilities, pokedexColor)
                PokemonSize(pokedexDetails.weight, pokedexDetails.height)
                PokemonStats(pokedexDetails.name, pokedexDetails.stats, pokedexColor)
            }
        }
    }
}

@Composable
fun PokemonImage(imageUrl: String, pokedexColor: PokedexColor, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(pokedexColor.color.copy(alpha = .5F)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(16.dp))
        ImageFromURL(imageUrl = imageUrl, size = 140.dp)
        Spacer(modifier = modifier.height(24.dp))
    }
}

@Composable
fun PokemonAbout(name: String, description: String, modifier: Modifier = Modifier) {
    Text(
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = lightScrim),
        text = stringResource(id = R.string.about_pokemon, name.replaceFirstChar { it.titlecase() })
    )
    Spacer(modifier = modifier.height(16.dp))
    Text(
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal, fontSize = 16.sp),
        text = description
    )
    Spacer(modifier = modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonAbilities(name: String, abilities: List<Pair<String, Boolean>>, pokedexColor: PokedexColor, modifier: Modifier = Modifier) {
    Text(
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = lightScrim),
        text = stringResource(id = R.string.pokemon_abilities, name.replaceFirstChar { it.titlecase() })
    )
    Spacer(modifier = modifier.height(8.dp))
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        abilities.forEach {
            val selectedTextColor = if (pokedexColor.color == lightOnPrimary) lightScrim else lightOnPrimary
            InputChip(
                shape = RoundedCornerShape(12.dp),
                label = { Text(style = MaterialTheme.typography.bodyMedium, text = it.first, color = lightScrim) },
                colors = InputChipDefaults.inputChipColors(selectedContainerColor = pokedexColor.color.copy(alpha = .5F), selectedLabelColor = selectedTextColor),
                selected = it.second,
                onClick = { },
            )
        }
    }
    Spacer(modifier = modifier.height(16.dp))
}

@Composable
fun PokemonSize(weight: Int, height: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = modifier.weight(.5F),
            text = stringResource(id = R.string.pokemon_weight, weight.toString())
        )
        Text(
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = modifier.weight(.5F),
            text = stringResource(id = R.string.pokemon_height, height.toString())
        )
    }
    Spacer(modifier = modifier.height(16.dp))
}

@Composable
fun PokemonStats(name: String, stats: List<Pair<String, Int>>, pokedexColor: PokedexColor, modifier: Modifier = Modifier) {
    Text(
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = lightScrim),
        text = stringResource(id = R.string.pokemon_stats, name.replaceFirstChar { it.titlecase() })
    )
    Spacer(modifier = modifier.height(8.dp))
    stats.forEach { stat ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
        ) {
            Text(
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal, fontSize = 16.sp),
                modifier = modifier.weight(.5F),
                text = stringResource(R.string.stat_value, stat.first.replaceFirstChar { it.titlecase() }, stat.second.toString()),
            )
            Box(
                modifier = modifier.weight(.5F)
            ) {
                val trackColor = if (pokedexColor.color == lightOutlineVariant) lightOutline else lightOutlineVariant
                LinearProgressIndicator(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    progress = (stat.second.toFloat() / 100),
                    color = pokedexColor.color.copy(.5F),
                    trackColor = trackColor
                )
            }
        }
    }
    Spacer(modifier = modifier.height(16.dp))
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