package com.bonface.pokespectra.features.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bonface.pokespectra.core.lightPrimary

@Composable
fun AppTopBar(titleText: String) {
    TopAppBar(
        title = {
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
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = titleText,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        backgroundColor = lightPrimary,
        contentColor = Color.Black,
        elevation = 0.dp,
    )
}