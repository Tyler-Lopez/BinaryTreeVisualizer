package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.*

// This is selectable from a drop-down, thus implements dropdownable
// Overrides .thumbnail to display a picture thumbnail
data class ComposableTreeTheme(
    val nodeColor: Color,
    val lineColor: Color,
    val selectedNodeColor: Color,
    // Used to make a more complicated thumbnail with Canvas if desired
    val drawSpecial: (DrawScope.() -> Unit)? = null
) : Dropdownable {

    @Composable
    override fun thumbnail() {
        val theme = this
        Box(modifier = Modifier
            .shadow(3.dp)
            .drawBehind {
                drawSpecial
            }) {
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
                    LightBlue,
                    Color(180, 104, 5),
                ),
                // GREEN
                ComposableTreeTheme(
                    Color(17, 135, 8),
                    Color(36, 173, 26),
                    Color(137, 0, 180)
                ),
                // RED
                ComposableTreeTheme(
                    DarkerRed,
                    Red,
                    Color(30, 122, 11)
                ),
                // YELLOW
                ComposableTreeTheme(
                    Color(237, 157, 7),
                    Color(232, 179, 21),
                    Color(126, 4, 128)
                ),
                // PINK
                ComposableTreeTheme(
                    Color(221, 36, 144),
                    Color(251, 62, 173),
                    Color(11, 143, 154)
                ),
                // HEART
                ComposableTreeTheme(
                    Color(221, 36, 144),
                    Color(251, 62, 173),
                    Color(11, 143, 154)
                ) {

                },
            )
        }
    }
}