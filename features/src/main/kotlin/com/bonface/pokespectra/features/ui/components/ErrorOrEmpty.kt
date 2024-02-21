package com.bonface.pokespectra.features.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bonface.pokespectra.core.fontFamily
import com.bonface.pokespectra.core.lightScrim
import com.bonface.pokespectra.features.R

@Composable
fun ErrorOrEmpty(errorMessage: String = stringResource(R.string.error_text)) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            modifier = Modifier
                .padding(16.dp),
            color = lightScrim,
            fontFamily = fontFamily
        )
    }
}