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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.AVLVisualizerTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AVLVisualizerTheme {



                // A surface container using the 'background' color from the theme
                var offsetX by remember {
                    mutableStateOf(1f)
                }
                var offsetY by remember {
                    mutableStateOf(1f)
                }
                var scale by remember {
                    mutableStateOf(1f)
                }


                Box(
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offsetX,
                            translationY = offsetY
                        )
                        .background(Color.Magenta)
                ) {
                    Tree()
                }
                ZoomableListener(
                    pan = { pan ->
                        offsetX += pan.x
                        offsetY += pan.y
                    },
                    zoom = { zoom ->
                        scale *= zoom
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

