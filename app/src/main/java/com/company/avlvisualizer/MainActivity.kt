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
import androidx.compose.ui.geometry.Size
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
        tree.insert(25)
        tree.insert(75)
        tree.insert(10)
        tree.insert(100)


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
                var nodeComposableDataList by remember {
                    mutableStateOf(tree.returnComposableData())
                }
                var activeNode by remember {
                    mutableStateOf("None Selected")
                }
                var treeStyle by remember {
                    mutableStateOf(ComposableTreeStyle())
                }
                Scaffold(
                    topBar = {
                        TopAppBar(modifier = Modifier, content = {

                            Button(
                                onClick = {
                                    tree.insert((Math.random() * 100).toInt())
                                    nodeComposableDataList = tree.returnComposableData()
                                }
                            ) {
                                Text("Insert Random")
                            }
                            Button(
                                onClick = {
                                    treeStyle = ComposableTreeStyle(ySpacing = treeStyle.ySpacing + 10f)
                                }
                            ) {
                                Text("+")
                            }
                            Button(
                                onClick = {
                                    treeStyle = ComposableTreeStyle(ySpacing = treeStyle.ySpacing - 10f)
                                }
                            ) {
                                Text("-")
                            }

                          //  Text(activeNode)
                        })
                    },
                    content = {
                        // A surface container using the 'background' color from the theme
                        // The Box contains the Composable Tree, and contains the pointerInput for dragging and zooming
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Green) // Just for debug
                        ) {
                            // The Tree is passed a modifier which changes in accordance with translation and scaling
                            ComposableTree(
                                data = nodeComposableDataList,
                                modifier = Modifier,
                                style = treeStyle
                            ) {
                                activeNode = it
                            }
                        }
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

