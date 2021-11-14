package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ZoomableColumn(
    modifier: Modifier
) {
    var scale by remember {
        mutableStateOf(1f)
    }
    var rotationState by remember {
        mutableStateOf(1f)
    }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape)
                .background(Color.Magenta)
                .pointerInput(Unit) {
                    detectTransformGestures {
                        _, _, zoom, rotation ->
                        scale *= zoom
                        rotationState += rotation
                    }
                }
        ) {
            Text("This is a test of zoom in and rotation")
        }

    }
}