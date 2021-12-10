package com.company.avlvisualizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.AVLVisualizerTheme
import kotlin.math.atan
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AVLVisualizerTheme {


                // A surface container using the 'background' color from the theme
                var offset by remember {
                    mutableStateOf(Offset.Zero)
                }
                var scale by remember {
                    mutableStateOf(1f)
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(40, 40, 40))
                )
                Box(
                    modifier = Modifier
                        .graphicsLayer(
                            translationX = -offset.x * scale,
                            translationY = -offset.y * scale,
                            scaleX = scale,
                            scaleY = scale,
                            transformOrigin = TransformOrigin(0f, 0f)
                        )
                ) {
                    Tree()
                }
                ZoomableListener(
                    // https://developer.android.com/reference/kotlin/androidx/compose/foundation/gestures/package-summary#(androidx.compose.ui.input.pointer.PointerInputScope).detectTransformGestures(kotlin.Boolean,kotlin.Function4)
                    listener = { centroid, pan, zoom ->
                        val oldScale = scale // Old Scale
                        scale *= zoom // New Scale
                        offset = // This is necessary to ensure we zoom where fingers are pinching
                            (offset + centroid / oldScale) - (centroid / scale + pan / oldScale)
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AVLVisualizerTheme {
        Greeting("Android")
    }
}

