package com.company.avlvisualizer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,

    background = Color.White,
    surface = Grey, // IMPORTANT FOR DROPDOWN!
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,

    background = Color.White,
    surface = DarkGrey, // IMPORTANT FOR DROPDOWN!
    onPrimary = DarkGrey,
    onSecondary = DarkGrey,
    onBackground = DarkGrey,
    onSurface = DarkGrey,

    )

@Composable
fun AVLVisualizerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors =
    //   val colors = if (darkTheme) {
    //      DarkColorPalette
        //   } else {
        LightColorPalette
//    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}