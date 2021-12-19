package com.company.avlvisualizer

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.*
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
                    mutableStateOf("No Node Selected")
                }
                var treeStyle by remember {
                    mutableStateOf(ComposableTreeStyle())
                }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .height(150.dp)
                                .drawBehind {
                                    drawLine(
                                        color = LightGrey,
                                        strokeWidth = 5f,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height)
                                    )
                                }
                                .shadow(5.dp),
                            backgroundColor = DarkGrey,
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp)
                                ) {
                                    // BEGIN INSERT ROW
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Button(
                                            onClick = {
                                                tree.insert((Math.random() * 100).toInt())
                                                nodeComposableDataList = tree.returnComposableData()
                                            },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Grey)
                                        ) {

                                            Text(
                                                text = "ADD RANDOM",
                                                fontSize = 25.sp,
                                                fontFamily = roboto,
                                                fontWeight = Normal,
                                                color = LightGrey
                                            )
                                        }
                                    } // END INSERT ROW
                                    // BEGIN SPACING AND BALANCE ROW
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(25.dp)
                                            .padding(bottom = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // BEGING SPACING COLUMN
                                        Row(
                                            modifier = Modifier.fillMaxHeight(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Filled.Expand, "Expand", tint = LightBlue,
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .padding(end = 2.dp)
                                            )
                                            Text(
                                                text = "SPACING:\t",
                                                color = LightGrey,
                                                fontFamily = roboto,
                                                fontWeight = Normal,
                                            )
                                            Icon(
                                                imageVector = Icons.Filled.ArrowUpward,
                                                contentDescription = "Increase Y-Spacing",
                                                tint = LightGrey,
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .padding(end = 2.dp)
                                                    .background(Grey)
                                                    .clickable {
                                                        treeStyle =
                                                            ComposableTreeStyle(ySpacing = treeStyle.ySpacing + 10f)
                                                    }
                                            )
                                            Icon(
                                                imageVector = Icons.Filled.ArrowDownward,
                                                contentDescription = "Decrease Y-Spacing",
                                                tint = LightGrey,
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .background(Grey)
                                                    .clickable {
                                                        treeStyle =
                                                            ComposableTreeStyle(ySpacing = treeStyle.ySpacing - 10f)
                                                    }
                                            )
                                        } // END SPACING COLUMN
                                        Row(
                                            modifier = Modifier.fillMaxHeight(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Filled.Timeline,
                                                "BALANCING",
                                                tint = LightBlue,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .padding(end = 2.dp)
                                            )
                                            Text(
                                                text = "BALANCE:\t",
                                                color = LightGrey,
                                                fontFamily = roboto,
                                                fontWeight = Normal,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .padding(end = 2.dp)
                                            )
                                            Text(
                                                text = "ON",
                                                color = LightGrey,
                                                textAlign = TextAlign.Center,
                                                fontFamily = roboto,
                                                fontWeight = Normal,

                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(35.dp)
                                                    .padding(end = 2.dp)
                                                    .background(Grey)
                                                    .clickable {
                                                        //
                                                    }
                                            )
                                            Text(
                                                text = "OFF",
                                                color = LightGrey,
                                                textAlign = TextAlign.Center,
                                                fontFamily = roboto,
                                                fontWeight = Normal,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(35.dp)
                                                    .padding(end = 2.dp)
                                                    .background(Grey)
                                                    .clickable {
                                                        //
                                                    }
                                            )
                                        }

                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = activeNode,
                                            color = LightGrey,
                                            fontFamily = roboto,
                                            fontWeight = Thin,
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                                //
                            }
                        )
                    },
                    content = {
                        // A surface container using the 'background' color from the theme
                        // The Box contains the Composable Tree, and contains the pointerInput for dragging and zooming
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            // The Tree is passed a modifier which changes in accordance with translation and scaling
                            ComposableTree(
                                data = nodeComposableDataList,
                                modifier = Modifier
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Grey,
                                                Grey
                                            )
                                        )
                                    ),
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

