package com.bonface.pokespectra.features.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

data class TopAppBarState(val title: String? = null, val color: Color? = null)

object TopAppBarStateProvider {
    var topAppBarState: TopAppBarState by mutableStateOf(TopAppBarState())
    fun update(topAppBarState: TopAppBarState) {
        this.topAppBarState = topAppBarState
    }
}