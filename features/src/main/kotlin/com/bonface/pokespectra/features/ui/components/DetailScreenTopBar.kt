package com.bonface.pokespectra.features.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bonface.pokespectra.core.lightOnPrimary
import com.bonface.pokespectra.core.lightOutline
import com.bonface.pokespectra.core.lightPrimary
import com.bonface.pokespectra.core.lightScrim
import com.bonface.pokespectra.features.R
import com.bonface.pokespectra.features.utils.TopAppBarStateProvider.topAppBarState

@Composable
fun DetailScreenTopBar(onBackPress: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            val iconTintColor = if (topAppBarState.color != null && topAppBarState.color == lightOnPrimary) lightOutline else lightOnPrimary
            OutlinedButton(onClick = onBackPress,
                modifier= Modifier.size(36.dp),
                shape = CircleShape,
                border= BorderStroke(1.dp, iconTintColor),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  iconTintColor)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = iconTintColor
                )
            }
        },
        title = {
            topAppBarState.title?.let { title ->
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.headlineSmall) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp),
                                textAlign = TextAlign.Justify,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = title,
                                color = if (topAppBarState.color != null && topAppBarState.color == lightOnPrimary) lightScrim else lightOnPrimary
                            )
                        }
                    }
                }
            }
        },
        backgroundColor = topAppBarState.color ?: lightPrimary,
        contentColor = lightScrim,
        elevation = 0.dp,
    )
}

@Preview
@Composable
fun DetailScreenTopBarPreview() {
    DetailScreenTopBar {}
}