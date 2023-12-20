package com.bonface.pokespectra.features.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bonface.pokespectra.core.lightPrimary
import com.bonface.pokespectra.core.lightScrim
import com.bonface.pokespectra.core.lightSecondary

@Preview
@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(width = 48.dp, height = 48.dp),
            color = lightSecondary
        )
        Text(
            text = "Loading...",
            modifier = Modifier.padding(16.dp),
            color = lightScrim
        )
    }
}