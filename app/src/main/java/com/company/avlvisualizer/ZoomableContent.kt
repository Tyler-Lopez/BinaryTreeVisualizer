package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout

@Composable
fun ZoomableBox(
    content: @Composable BoxScope.() -> Unit
) {
    var offsetX by remember {
        mutableStateOf(1f)
    }
    var offsetY by remember {
        mutableStateOf(1f)
    }
    var scale by remember {
        mutableStateOf(1f)
    }
    var rotationState by remember {
        mutableStateOf(1f)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    offsetX += pan.x
                    offsetY += pan.y
                    scale *= zoom
                    rotationState += rotation
                }
            }
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                scaleX = maxOf(
                    0.5f, minOf(3f, scale)
                ),
                scaleY = maxOf(
                    0.5f, minOf(3f, scale)
                ),
                translationX = offsetX,
                translationY = offsetY
            )
        ) {
            content()
        }
    }
}