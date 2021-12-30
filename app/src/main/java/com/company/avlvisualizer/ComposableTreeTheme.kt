package com.company.avlvisualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.*

data class ComposableTreeTheme(
    val nodeColor: Color,
    val lineColor: Color,
) : Dropdownable {

    @Composable
    override fun thumbnail() {
        val theme = this
        Box(modifier = Modifier.shadow(3.dp)) {
            Text(
                text = "C",
                fontSize = 0.sp,
                color = theme.lineColor,
                modifier = Modifier
                    .width(25.dp)
                    .height(15.dp)
                    .background(theme.lineColor)
            )
        }
    }

    companion object {
        fun getThemes(): List<ComposableTreeTheme> {
            return listOf(
                // BLUE
                ComposableTreeTheme(
                    Color(16, 90, 201),
                    LightBlue
                ),
                // GREEN
                ComposableTreeTheme(
                    Color(17, 135, 8),
                    Color(36, 173, 26)
                ),
                // RED
                ComposableTreeTheme(DarkerRed, Red),
                // YELLOW
                ComposableTreeTheme(
                    Color(237, 157, 7),
                    Color(232, 179, 21)
                ),
                // PINK
                ComposableTreeTheme(
                    Color(221, 36, 144),
                    Color(251, 62, 173)
                ),
            )
        }
    }
}