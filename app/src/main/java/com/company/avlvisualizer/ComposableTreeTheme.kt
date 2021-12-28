package com.company.avlvisualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.company.avlvisualizer.ui.theme.*

enum class ComposableTreeTheme(
    val nodeColor: Color,
    val lineColor: Color,
    val selectedNodeColor: Color,
) {
    BLUE(Color(16, 90, 201), Color(38, 125, 255), Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.lineColor),
                onClick = { onClick(this) }
            ) { }
        }
    },
    RED(DarkerRed, Red, Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.lineColor),
                onClick = { onClick(this) }
            ) { }
        }
    },
    GREEN(Color(17, 135, 8), Color(36, 173, 26), Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.lineColor),
                onClick = { onClick(this) }
            ) { }
        }
    },
    YELLOW(Color(237, 157, 7), Color(232, 179, 21), Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.lineColor),
                onClick = { onClick(this) }
            ) { }
        }
    },
    PURPLE(Purple, Color.Magenta, Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.lineColor),
                onClick = { onClick(this) }
            ) { }
        }
    };

    @Composable
    abstract fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit)

    companion object {
        fun getThemes(): List<ComposableTreeTheme> {
            return listOf(BLUE, GREEN, YELLOW, PURPLE, RED)
        }
    }
}