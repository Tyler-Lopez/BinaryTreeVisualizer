package com.company.avlvisualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ComposableTree(
    modifier: Modifier = Modifier,
    style: ComposableTreeStyle = ComposableTreeStyle()
) {
    var scale by remember {
        mutableStateOf(1f)
    }
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    Canvas(modifier = modifier
        .pointerInput(Unit) {
            detectTransformGestures { centroid, pan, zoom, _ ->
                val oldScale = scale // Old Scale
                scale *= zoom // New Scale
                offset =
                        // This is necessary to ensure we zoom where fingers are pinching
                    (offset + centroid / oldScale) - (centroid / scale + pan / oldScale)
            }
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {

                }
            )
        }.graphicsLayer {
            scaleX = scale
            scaleY = scale
            transformOrigin = TransformOrigin(0f, 0f)
        }) {
        center = this.center
        drawCircle(
            center = center - offset,
            color = Color.Black,
            radius = 10f
        )
    }
}