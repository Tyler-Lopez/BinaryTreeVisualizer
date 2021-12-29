package com.company.avlvisualizer

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ResetTv
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.company.avlvisualizer.ui.theme.*
import kotlinx.coroutines.launch
import java.lang.Float.max

class MainActivity : ComponentActivity() {

    @ExperimentalUnitApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        // https://stackoverflow.com/questions/68980068/jetpack-compose-status-bar-color-not-updated-in-dark-theme
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)


        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            // Generate the Tree Data Structure... this is not ideal - must be up here to avoid constantly regenerated on recompose
            var tree by remember { mutableStateOf(BinaryTree()) }
            // End tree data structure

            AVLVisualizerTheme {
                var balanceType by remember {
                    mutableStateOf(BinaryTreeBalanceType.UNBALANCED)
                }
                var nodeComposableDataList by remember {
                    mutableStateOf(tree.returnComposableData())
                }
                var treeStyle by remember {
                    mutableStateOf(ComposableTreeStyle())
                }

                // https://levelup.gitconnected.com/implement-android-snackbar-in-jetpack-compose-d83df5ff5b47
                // https://www.devbitsandbytes.com/configuring-snackbar-jetpack-compose-using-scaffold-with-bottom-navigation/
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()


                Scaffold(
                    topBar = {
                        ComposableTopBar(
                            message = "",
                            onSpacingChange = {
                                if (treeStyle.ySpacing <= 10 && it < 0) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Cannot decrease spacing further."
                                        )
                                    }
                                } else treeStyle.ySpacing += (it + ((if (it < 0) -1 else 1) * (treeStyle.ySpacing * 0.1f)))
                            },
                            onWeightChange = {
                                if (treeStyle.nodeSize <= 80 && it < 0) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Cannot decrease width further."
                                        )
                                    }
                                } else {
                                    treeStyle.nodeSize += ((if (it < 0) -1 else 1) * (treeStyle.nodeSize * 0.2f))
                                    treeStyle.lineWidth += ((if (it < 0) -1 else 1) * (treeStyle.lineWidth * 0.2f))
                                }
                            },
                            onBalanceChange = {
                                balanceType = it
                                if (balanceType == BinaryTreeBalanceType.AVL_TREE) {
                                    tree = tree.balanceTree()
                                    nodeComposableDataList = tree.returnComposableData()
                                }
                            },
                            onThemeChange = {
                                treeStyle.theme = it
                            },
                            onRandomNumber = {
                                tree.insert(it, balanceType == BinaryTreeBalanceType.AVL_TREE)
                                nodeComposableDataList = tree.returnComposableData()
                            },
                            onInsert = {
                                try {
                                    val inputVal = it.toInt()
                                    if (inputVal < 0 || inputVal > 999) throw Exception()
                                    tree.insert(
                                        inputVal,
                                        balanceType == BinaryTreeBalanceType.AVL_TREE
                                    )
                                    nodeComposableDataList = tree.returnComposableData()
                                } catch (e: Exception) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Input must be an integer in the range of 0 to 999."
                                        )
                                    }
                                }
                            }
                        )
                    },
                    scaffoldState = scaffoldState,
                    snackbarHost = { scaffoldState.snackbarHostState }
                ) {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Grey)
                    ) {
                        val boxWithConstraintsScope = this
                        if (nodeComposableDataList.isNotEmpty()) {
                            ComposableTree(
                                data = nodeComposableDataList,
                                style = treeStyle
                            ) {
                                //  activeNode = it
                            }
                            ComposableResetButton(
                                modifier = Modifier
                                    .size(45.dp)
                                    .offset(y = maxHeight - 45.dp)
                                    .padding(5.dp)
                                    .shadow(5.dp),
                                color = Red
                            ) {
                                // Reset tree nodes, y-spacing and thickness
                                tree = BinaryTree()
                                val tmpTheme = treeStyle.theme
                                treeStyle = ComposableTreeStyle()
                                treeStyle.theme = tmpTheme
                                nodeComposableDataList = tree.returnComposableData()
                            }
                        } else {
                            val circleRadius = (LocalDensity.current.run {
                                minOf(
                                    boxWithConstraintsScope.maxWidth.toPx(),
                                    boxWithConstraintsScope.maxHeight.toPx()
                                )
                            } / 2f) * 0.9f
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                // Draw Text
                                val paint = Paint()
                                val roboto = ResourcesCompat.getFont(context, R.font.roboto_medium)
                                paint.textAlign = Paint.Align.CENTER
                                paint.textSize = circleRadius / 4f
                                paint.color = 0xff6592b8.toInt()
                                paint.typeface = roboto

                                val paintSub = Paint()
                                paintSub.textAlign = Paint.Align.CENTER
                                paintSub.textSize = circleRadius / 7f
                                paintSub.color = 0xffc4c4c4.toInt()
                                paintSub.typeface = roboto


                                drawIntoCanvas {
                                    it.nativeCanvas.drawText(
                                        "EMPTY TREE",
                                        center.x,
                                        center.y,
                                        paint
                                    )
                                    it.nativeCanvas.drawText(
                                        "Insert a number to begin",
                                        center.x,
                                        center.y + (circleRadius) / 4f,
                                        paintSub
                                    )
                                }
                            }
                        }

                        ComposableSnackbar(
                            snackbarHostState = scaffoldState.snackbarHostState,
                            modifier = Modifier.align(Alignment.BottomCenter),
                        )
                    }
                }
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

