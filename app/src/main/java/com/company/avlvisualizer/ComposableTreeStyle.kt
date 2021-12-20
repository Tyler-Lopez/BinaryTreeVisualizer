package com.company.avlvisualizer

import androidx.compose.ui.graphics.Color
import com.company.avlvisualizer.ui.theme.*

data class ComposableTreeStyle(
    var theme: ComposableTreeTheme = ComposableTreeTheme.BLUE,
    val nodeSize: Float = 80f,
    var ySpacing: Float = 5f,
    val lineWidth: Float = 60f,
    val textColor: Color = LightGrey
)
