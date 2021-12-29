package com.company.avlvisualizer

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

interface Dropdownable {
    @Composable
    fun thumbnail() {
        Text(
            text = "$this",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}