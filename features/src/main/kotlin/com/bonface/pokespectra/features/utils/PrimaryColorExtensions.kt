package com.bonface.pokespectra.features.utils

object PrimaryColorExtensions {
    fun getPrimaryColor(color: String): PrimaryColor {
        return when {
            color.contentEquals(PrimaryColor.White.name, true) -> PrimaryColor.White
            color.contentEquals(PrimaryColor.Black.name, true) -> PrimaryColor.Black
            color.contentEquals(PrimaryColor.Blue.name, true) -> PrimaryColor.Blue
            color.contentEquals(PrimaryColor.Red.name, true) -> PrimaryColor.Red
            color.contentEquals(PrimaryColor.Green.name, true) -> PrimaryColor.Green
            color.contentEquals(PrimaryColor.Yellow.name, true) -> PrimaryColor.Yellow
            color.contentEquals(PrimaryColor.Gray.name, true) -> PrimaryColor.Gray
            color.contentEquals(PrimaryColor.Brown.name, true) -> PrimaryColor.Brown
            color.contentEquals(PrimaryColor.Pink.name, true) -> PrimaryColor.Pink
            color.contentEquals(PrimaryColor.Purple.name, true) -> PrimaryColor.Purple
            else -> PrimaryColor.NotSpecified
        }
    }
}