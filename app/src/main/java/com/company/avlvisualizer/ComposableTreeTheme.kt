package com.company.avlvisualizer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.company.avlvisualizer.ui.theme.*

enum class ComposableTreeTheme(
    val nodeColor: Color,
    val lineColor: Color,
    val selectedNodeColor: Color,
) {
    BLUE(DarkBlue, DarkerBlue, Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.nodeColor),
                onClick = { onClick(this) }
            ) { }
        }
    },
    RED(Red, DarkerRed, Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.nodeColor),
                onClick = { onClick(this) }
            ) { }
        }
    },
    GREEN(Green, DarkerGreen, Grey) {
        @Composable
        override fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit) {
            Button(
                modifier = androidx.compose.ui.Modifier
                    .size(height)
                    .padding(start = 5.dp)
                    .padding(vertical = 5.dp)
                    .border(width = 1.dp, color = LightGrey),
                colors = ButtonDefaults.buttonColors(backgroundColor = this.nodeColor),
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
                colors = ButtonDefaults.buttonColors(backgroundColor = this.nodeColor),
                onClick = { onClick(this) }
            ) { }
        }
    };

    @Composable
    abstract fun thumbnail(height: Dp, onClick: (ComposableTreeTheme) -> Unit)

    companion object {
        fun getThemes(): List<ComposableTreeTheme> {
            return listOf(BLUE, RED, PURPLE, GREEN)
        }
    }
}