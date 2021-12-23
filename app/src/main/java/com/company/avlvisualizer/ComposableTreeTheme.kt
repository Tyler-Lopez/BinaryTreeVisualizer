package com.company.avlvisualizer

import androidx.compose.ui.graphics.Color
import com.company.avlvisualizer.ui.theme.DarkBlue
import com.company.avlvisualizer.ui.theme.Grey
import com.company.avlvisualizer.ui.theme.Red

enum class ComposableTreeTheme(
    val nodeColor: Color,
    val lineColor: Color,
    val selectedNodeColor: Color,
) {
    BLUE(DarkBlue, Red, Grey),
    RED(Red, DarkBlue, Grey);

}