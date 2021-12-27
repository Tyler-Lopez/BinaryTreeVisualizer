package com.company.avlvisualizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
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
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (nodeComposableDataList.isNotEmpty()) {
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
                              //  activeNode = it
                            }
                        } else {
                            Text("THE TREE IS EMPTY\nINSERT A VALUE TO BEGIN")
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

