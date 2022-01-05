package com.company.avlvisualizer.domain


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.ui.res.imageResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.R
import com.company.avlvisualizer.ui.theme.*

// This is selectable from a drop-down, thus implements dropdownable
// Overrides .Thumbnail to display a picture Thumbnail
data class ComposableTreeTheme(
    val nodeColor: Color,
    val lineColor: Color,
    val selectedNodeColor: Color,
    // Used to make a more complicated Thumbnail with Canvas if desired
    val imageId: Int = -1,
    val selectedImageId: Int = -1

) : Dropdownable {
    @Composable
    override fun Thumbnail() {
        val theme = this
        Box(
            modifier = Modifier
                .shadow(3.dp)
        ) {
            Text(
                text = "C",
                fontSize = 0.sp,
                color = theme.lineColor,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
                    .background(theme.lineColor)
            )
            if (imageId != -1) {
                val image = ImageBitmap.imageResource(id = imageId)
                val aspectRatio = (image.width.toFloat()) / image.height

                Canvas(
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                ) {
                    drawImage(
                        image = image,
                        dstSize = IntSize(
                            width = (25.dp.toPx() * aspectRatio).toInt(),
                            height = (25.dp.toPx() * aspectRatio).toInt()
                        )
                    )
                }
            }
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
                    Color(11, 143, 154),
                ),
                // HEART

                ComposableTreeTheme(
                    Grey,
                    DarkGrey,
                    Color(11, 143, 154),
                    R.drawable.peach,
                    R.drawable.eggplant
               ),
            )
        }
    }
}