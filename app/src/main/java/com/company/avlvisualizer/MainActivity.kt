package com.company.avlvisualizer

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.*
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @ExperimentalUnitApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {

        // Invoke the onCreate function in the superclass
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            // Define MediaPlayer objects with click and select sounds
            val applicationContext = this
            val selectMp: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.node_select)
            val clickMp: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.click_sound)

            // Define vibrator object
            val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            // Define the tree as a mutable BinaryTree object
            var tree by remember { mutableStateOf(BinaryTree()) }


            AVLVisualizerTheme {

                // Define mutable variables which impact selection and style
                var balanceType by remember {
                    mutableStateOf(BinaryTreeBalanceType.UNBALANCED)
                }
                var treeStyle by remember {
                    mutableStateOf(ComposableTreeStyle())
                }
                var selectedIndex by remember {
                    mutableStateOf(-1)
                }

                // Passed into composable which draws the tree
                var nodeComposableDataList by remember {
                    mutableStateOf(tree.returnComposableData())
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
                                if (tree.size > 513) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Maximum tree size reached"
                                        )
                                    }
                                }
                                for (i in 1..it) {
                                    if (tree.size <= 512) {
                                        var randomNumber = Random.nextInt(0, 999)
                                        while (tree.contains(randomNumber)) randomNumber =
                                            Random.nextInt(0, 999)
                                        tree.insert(
                                            value = randomNumber,
                                            avlInsert = balanceType == BinaryTreeBalanceType.AVL_TREE
                                        )
                                    }
                                }
                                nodeComposableDataList = tree.returnComposableData()
                            },
                            onInsert = {
                                try {
                                    val inputVal = it.toInt()
                                    if (tree.contains(inputVal)) throw Exception("$inputVal is already present in this Tree")
                                    if (inputVal < 0 || inputVal > 999) throw Exception()
                                    tree.insert(
                                        inputVal,
                                        balanceType == BinaryTreeBalanceType.AVL_TREE
                                    )
                                    nodeComposableDataList = tree.returnComposableData()
                                } catch (e: NumberFormatException) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Input must be an integer in the range of 0 to 999"
                                        )
                                    }
                                } catch (e: Exception) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = e.message
                                                ?: "Input must be an integer in the range of 0 to 999"
                                        )
                                    }
                                }
                            },
                            onHapticFeedback = {
                                vibrate(vibrator, clickMp)
                            }
                        )
                    },
                    bottomBar = {
                        if (nodeComposableDataList.isNotEmpty()) {
                            ComposableBottomBar(
                                selected = selectedIndex,
                                onReset = {
                                    selectedIndex = -1
                                    vibrate(vibrator, selectMp)
                                    tree = BinaryTree()
                                    val tmpTheme = treeStyle.theme
                                    treeStyle = ComposableTreeStyle()
                                    treeStyle.theme = tmpTheme
                                    nodeComposableDataList = tree.returnComposableData()
                                },
                                onRemove = {
                                    vibrate(vibrator, selectMp)
                                    tree.remove(
                                        selectedIndex,
                                        balanceType == BinaryTreeBalanceType.AVL_TREE
                                    )
                                    selectedIndex = -1
                                    nodeComposableDataList = tree.returnComposableData()
                                }
                            )
                        }
                    },
                    scaffoldState = scaffoldState,
                    snackbarHost = { scaffoldState.snackbarHostState }
                ) {


                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Grey)
                            .padding(bottom = if (nodeComposableDataList.isNotEmpty()) 55.dp else 0.dp)
                    ) {
                        val boxWithConstraintsScope = this
                        if (nodeComposableDataList.isNotEmpty()) {
                            ComposableTree(
                                data = nodeComposableDataList,
                                style = treeStyle,
                                onNodeSelect = {
                                    if (it != null) vibrate(vibrator, selectMp)
                                    selectedIndex = it ?: -1
                                }
                            )
                        } else {
                            ComposableEmptyTree()
                        }

                        ComposableSnackbar(
                            snackbarHostState = scaffoldState.snackbarHostState,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 5.dp),
                        )
                    }
                }
            }
        }
    }
}

// Function invoked to vibrate and play MediaPlayer sound
fun vibrate(vibrator: Vibrator, mp: MediaPlayer) {
    mp.start()
    if (vibrator.hasVibrator()) { // Vibrator availability checking
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    30,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            ) // New vibrate method for API Level 26 or higher
        } else {
            vibrator.vibrate(30) // Vibrate method for below API Level 26
        }
    }
}
