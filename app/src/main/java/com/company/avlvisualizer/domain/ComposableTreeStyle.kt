package com.company.avlvisualizer.domain

import androidx.compose.ui.graphics.Color
import com.company.avlvisualizer.domain.ComposableTreeTheme
import com.company.avlvisualizer.ui.theme.*

data class ComposableTreeStyle(
    var theme: ComposableTreeTheme = ComposableTreeTheme.getThemes()[0],
    var nodeSize: Float = 80f,
    var ySpacing: Float = 5f,
    var lineWidth: Float = 60f,
    val textColor: Color = LightGrey
)
