package com.company.avlvisualizer.domain

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.LightGrey

interface Dropdownable {
    @Composable
    fun Thumbnail() {
        Text(
            text = "$this",
            color = Color.White
        )
    }
}