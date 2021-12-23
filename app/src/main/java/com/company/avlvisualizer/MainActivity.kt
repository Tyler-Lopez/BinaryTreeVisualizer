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
                var inputStr by remember {
                    mutableStateOf("")
                }

                // https://levelup.gitconnected.com/implement-android-snackbar-in-jetpack-compose-d83df5ff5b47
                // https://www.devbitsandbytes.com/configuring-snackbar-jetpack-compose-using-scaffold-with-bottom-navigation/
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                // https://stackoverflow.com/questions/59133100/how-to-close-the-virtual-keyboard-from-a-jetpack-compose-textfield
                val focusManager = LocalFocusManager.current

                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .height(145.dp)
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
                                    // BEGIN INSERT RANDOM AND SPECIFIC ROW
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(55.dp)
                                            .padding(bottom = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // BEGIN INSERT RANDOM COLUMN
                                        Row(
                                            modifier = Modifier.fillMaxHeight(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Button(
                                                onClick = {
                                                    tree.insert((Math.random() * 100).toInt())
                                                    nodeComposableDataList =
                                                        tree.returnComposableData()
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
                                        } // END INSERT RANDOM COLUMN
                                        // BEGIN INSERT SPECIFIC COLUMN
                                        Row(
                                            modifier = Modifier.fillMaxHeight(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            TextField(
                                                value = inputStr,
                                                modifier = Modifier.fillMaxHeight(),
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    keyboardType = KeyboardType.Number
                                                ),
                                                keyboardActions = KeyboardActions(onDone = {
                                                    // Close keyboard
                                                    focusManager.clearFocus()
                                                    // Handle user-input and edge cases
                                                    try {
                                                        val inputVal = inputStr.toInt()
                                                        if (inputVal < 0 || inputVal > 999) throw Exception()
                                                        tree.insert(inputVal)
                                                        nodeComposableDataList =
                                                            tree.returnComposableData()
                                                    } catch (e: Exception) {
                                                        scope.launch {
                                                            scaffoldState.snackbarHostState.showSnackbar(
                                                                message = "Input must be an integer in the range of 0 to 999."
                                                            )
                                                        }
                                                    }
                                                    // Reset user-input string
                                                    inputStr = ""
                                                }),
                                                onValueChange = {
                                                    inputStr = it
                                                },
                                                singleLine = true,
                                                placeholder = {
                                                    Text(text = "(0 - 999)")
                                                }
                                            )
                                        } // END INSERT SPECIFIC COLUMN
                                    } // END INSERT RANDOM AND INSERT SPECIFIC ROW
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
                                                        treeStyle.ySpacing =
                                                            treeStyle.ySpacing + 10f
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
                                                        treeStyle.ySpacing =
                                                            treeStyle.ySpacing - 10f
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
                                    } // END SPACING AND BALANCE ROW
                                    // BEGIN THEME SELECTION ROW
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
                                                Icons.Filled.Brush, "Theme", tint = LightBlue,
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .padding(end = 2.dp)
                                            )
                                            Text(
                                                text = "THEME:\t",
                                                color = LightGrey,
                                                fontFamily = roboto,
                                                fontWeight = Normal,
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(35.dp)
                                                    .padding(end = 2.dp)
                                                    .background(LightBlue)
                                                    .border(1.dp, LightGrey)
                                                    .clickable {
                                                        treeStyle.theme = ComposableTreeTheme.BLUE
                                                    }
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(35.dp)
                                                    .padding(end = 2.dp)
                                                    .background(Red)
                                                    .border(1.dp, LightGrey)
                                                    .clickable {
                                                        treeStyle.theme = ComposableTreeTheme.RED
                                                    }
                                            )
                                        } // END THEME SELECTION COLUMN
                                    } // END THEME SELECTION ROW
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
                    scaffoldState = scaffoldState,
                    snackbarHost = { scaffoldState.snackbarHostState }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
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

                        ComposableSnackbar(
                            snackbarHostState = scaffoldState.snackbarHostState,
                            modifier = Modifier.align(Alignment.BottomCenter)
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

