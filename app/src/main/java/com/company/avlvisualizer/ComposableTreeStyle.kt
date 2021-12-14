package com.company.avlvisualizer

import androidx.compose.ui.graphics.Color

data class ComposableTreeStyle(
    val lineColor: Color = Color.Green,
    val nodeColor: Color = Color.Magenta,
    val selectedNodeColor: Color = Color.Cyan,
    val selectedNodeBorderColor: Color = Color.White,
    val nodeSize: Float = 20f,
    val lineWidth: Float = 20f,
    val textColor: Color = Color.White
)
