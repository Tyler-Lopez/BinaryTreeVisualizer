package com.company.avlvisualizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.AVLVisualizerTheme
import kotlin.math.atan
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        // Generate the Tree Data Structure... this is not ideal - must be up here to avoid constantly regenerated on recompose
        val tree = BinaryTree()
        tree.insert(50)
        for (i in 0..50) {
            tree.insert((Math.random() * 100).toInt())
        }
        val nodeComposableDataList = tree.returnComposableData()

        val nodes = mutableListOf<BinaryNode?>()
        val offsets = mutableListOf<Offset>()
        val parentOffsets = mutableListOf<Offset>()
        tree.traversePreOrder { offset, bstNode, parentOffset ->
            parentOffsets.add(parentOffset ?: Offset(0f, 0f))
            offsets.add(offset)
            nodes.add(bstNode)
        }

        // End tree data structure

        super.onCreate(savedInstanceState)
        setContent {
            AVLVisualizerTheme {
                var activeNode by remember {
                    mutableStateOf("None Selected")
                }
                Scaffold(
                    topBar = {
                        TopAppBar(content = {
                            Text(activeNode)
                        })
                    },
                    content = {
                        // A surface container using the 'background' color from the theme
                        var offset by remember {
                            mutableStateOf(Offset.Zero)
                        }
                        var scale by remember {
                            mutableStateOf(1f)
                        }
                        // The Box contains the Composable Tree, and contains the pointerInput for dragging and zooming
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Green)
                        ) {
                            // The Tree is passed a modifier which changes in accordance with translation and scaling
                            ComposableTree(
                                data = nodeComposableDataList,
                                modifier = Modifier
                                    .requiredSize(10000.dp)
                                    .background(Color.DarkGray)
                                    .pointerInput(Unit) {
                                        // DRAG AND ZOOM
                                        detectTransformGestures { centroid, pan, zoom, _ ->
                                            val oldScale = scale // Old Scale
                                            scale *= zoom // New Scale
                                            offset =
                                                    // This is necessary to ensure we zoom where fingers are pinching
                                                (offset + centroid / oldScale) - (centroid / scale + pan / oldScale)
                                        }
                                    }
                                    .graphicsLayer {
                                        // APPLY ZOOM
                                        translationX = -offset.x * scale
                                        translationY = -offset.y * scale
                                        scaleX = scale
                                        scaleY = scale
                                        transformOrigin = TransformOrigin(0f, 0f)
                                    }
                            ) {
                                activeNode = it
                            }
                        }

                        // Commenting this out temporarily to find a better way to represent the tree
                        /*
                        ZoomableListener(
                            // https://developer.android.com/reference/kotlin/androidx/compose/foundation/gestures/package-summary#(androidx.compose.ui.input.pointer.PointerInputScope).detectTransformGestures(kotlin.Boolean,kotlin.Function4)
                            transformListener = { centroid, pan, zoom ->
                                val oldScale = scale // Old Scale
                                scale *= zoom // New Scale
                                offset =
                                        // This is necessary to ensure we zoom where fingers are pinching
                                    (offset + centroid / oldScale) - (centroid / scale + pan / oldScale)
                            },
                            tapListener = {
                                activeNode = "Offset = $offset OnTap = $it New = ${offset + it}"

                            }
                        )

                        Tree(
                            modifier = Modifier.graphicsLayer(
                                translationX = -offset.x * scale,
                                translationY = -offset.y * scale,
                                scaleX = scale,
                                scaleY = scale,
                                transformOrigin = TransformOrigin(0f, 0f)
                            ),
                            nodes = nodes,
                            offsets = offsets,
                            parentOffsets = parentOffsets,
                            nodeSelect = {
                                activeNode = it
                            }
                        )
                        */

                    })
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

