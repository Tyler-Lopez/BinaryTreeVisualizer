package com.company.avlvisualizer

import androidx.compose.ui.graphics.Color
import com.company.avlvisualizer.ui.theme.*

enum class ComposableTreeTheme(
    val nodeColor: Color,
    val lineColor: Color,
    val selectedNodeColor: Color,
) {
    BLUE(DarkBlue, Red, Grey),
    RED(Red, DarkBlue, Grey),
    GREEN(Green, Purple, Grey),
    PURPLE(Purple, Color.Magenta, Grey)
}