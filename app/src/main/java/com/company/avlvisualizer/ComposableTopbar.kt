package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.company.avlvisualizer.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow
import kotlin.random.Random

@ExperimentalUnitApi
@Composable
fun ComposableTopBar(
    message: String,
    onSpacingChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    onBalanceChange: (BinaryTreeBalanceType) -> Unit,
    onThemeChange: (ComposableTreeTheme) -> Unit,
    onRandomNumber: (Int) -> Unit,
    onInsert: (String) -> Unit
) {
    // https://stackoverflow.com/questions/59133100/how-to-close-the-virtual-keyboard-from-a-jetpack-compose-textfield
    val focusManager = LocalFocusManager.current
    var inputStr by remember {
        mutableStateOf("")
    }
    // https://foso.github.io/Jetpack-Compose-Playground/material/dropdownmenu/
    var expandedBalance by remember { mutableStateOf(false) }
    var expandedColor by remember { mutableStateOf(false) }
    val itemsBalance = BinaryTreeBalanceType.getBalanceTypes()
    val itemsColors = ComposableTreeTheme.getThemes()
    var selectedIndexBalance by remember { mutableStateOf(0) }
    var selectedIndexColor by remember { mutableStateOf(0) }



    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth()
        .shadow(3.dp)
        .background(DarkGrey)
        .padding(bottom = 5.dp)
        .drawBehind {
            drawLine(
                color = LightGrey,
                strokeWidth = 5f,
                start = Offset(0f, size.height.plus(10)),
                end = Offset(size.width, size.height.plus(10))
            )
        }
    ) {
        val maxHeight = this.maxHeight
        val maxWidth = this.maxWidth
        Column {
            Row(modifier = Modifier.height(60.dp).padding(bottom=5.dp), verticalAlignment = Alignment.CenterVertically) {


                Box(
                    modifier = Modifier
                        //.weight(0.6f)
                        .fillMaxHeight()
                        .shadow(5.dp)
                        .background(Grey)
                        .padding(10.dp),
                    contentAlignment = CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add Random",
                            color = LightGrey,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Button(modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .padding(end = 5.dp),
                               // .border(1.dp, LightBlue),
                                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGrey),
                                onClick = {
                                    onRandomNumber(Random.nextInt(0, 999))
                                }, contentPadding = PaddingValues(1.dp)
                            ) {
                                Text(
                                    text = "1",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontFamily = roboto, modifier = Modifier
                                )
                            }
                            Button(modifier = Modifier
                                .width(50.dp)
                                .height(40.dp)
                                .padding(start = 5.dp),
                              //  .border(1.dp, LightBlue),
                                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGrey),
                                onClick = {
                                    for (i in 1..10) onRandomNumber(Random.nextInt(0, 999))
                                }, contentPadding = PaddingValues(1.dp)
                            ) {
                                Text(
                                    text = "10",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontFamily = roboto, modifier = Modifier,
                                    letterSpacing = TextUnit(0f, TextUnitType.Sp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))

                Box(
                    modifier = Modifier
                 //       .weight(0.45f)
                        .shadow(5.dp)
                ) {
                    TextField(
                        value = inputStr,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Grey),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            // Close keyboard
                            focusManager.clearFocus()
                            // Handle user-input and edge cases
                            onInsert(inputStr)
                            // Reset user-input string
                            inputStr = ""
                        }),
                        onValueChange = {
                            inputStr = it
                        },
                        singleLine = true,
                        label = {
                            Text(text = "Insert Number", color = LightGrey)
                        },
                        placeholder = {
                            Text(text = "(0 - 999)", color = White)
                        },
                        colors = TextFieldDefaults.textFieldColors(textColor = White)
                    )
                }



            }
            FlowRow(modifier = Modifier) {
                // Y-SPACING
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Expand,
                        title = null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = "Increase",
                                modifier = Modifier
                                    .weight(0.5f)
                                    .size(it.times(0.7f))
                                    .clickable { onSpacingChange(+10) },
                                tint = LightBlue
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = "Decrease",
                                modifier = Modifier
                                    .weight(0.5f)
                                    .size(it.times(0.7f))
                                    .clickable { onSpacingChange(-10) },
                                tint = LightBlue
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.LineWeight,
                        title = null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = "Increase",
                                modifier = Modifier
                                    .weight(0.5f)
                                    .size(it.times(0.7f))
                                    .clickable { onWeightChange(+100) },
                                tint = LightBlue
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = "Decrease",
                                modifier = Modifier
                                    .weight(0.5f)
                                    .size(it.times(0.7f))
                                    .clickable { onWeightChange(-100) },
                                tint = LightBlue
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Brush,
                        title = null
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                      //          .fillMaxWidth()
                                .height(it),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            ComposeMenu(
                          //      width = this.maxWidth,
                                menuItems = itemsColors,
                                menuExpandedState = expandedColor,
                                selectedIndex = selectedIndexColor,
                                updateMenuExpandStatus = {
                                    expandedColor = true
                                    expandedBalance = false
                                },
                                onDismissMenuView = {
                                    expandedColor = false
                                },
                                onMenuItemclick = { index ->
                                    selectedIndexColor = index
                                    onThemeChange(
                                        itemsColors[selectedIndexColor]
                                    )
                                    expandedColor = false
                                }
                            )
                        }
                    }
                }
                val sizeAvailableOnRow =  maxWidth.minus(360.dp).minus(1.dp)
                Box(
                    modifier = Modifier
                        .width(if (sizeAvailableOnRow >= 230.dp) sizeAvailableOnRow else maxWidth)
                        .height(50.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Insights,
                        title = "BALANCE"
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(it)
                                .background(Color.Magenta),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            ComposeMenu(
                         //       width = this.maxWidth,
                                menuItems = itemsBalance,
                                menuExpandedState = expandedBalance,
                                selectedIndex = selectedIndexBalance,
                                updateMenuExpandStatus = {
                                    expandedBalance = true
                                    expandedColor = false
                                },
                                onDismissMenuView = {
                                    expandedBalance = false
                                },
                                onMenuItemclick = { index ->
                                    selectedIndexBalance = index
                                    onBalanceChange(itemsBalance[selectedIndexBalance])
                                    expandedBalance = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}