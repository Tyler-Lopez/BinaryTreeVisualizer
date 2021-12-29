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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

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
        .background(DarkGrey)
        .padding(bottom = 5.dp)
      //  .shadow(3.dp)
        .drawBehind {
            drawLine(
                color = LightGrey,
                strokeWidth = 3f,
                start = Offset(0f, size.height.plus(10)),
                end = Offset(size.width, size.height.plus(10))
            )
        }
    ) {
        val maxHeight = this.maxHeight
        val maxWidth = this.maxWidth
        Column {
            Row(modifier = Modifier.height(75.dp)) {
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                        .padding(5.dp)
                        .background(Grey)
                        .border(width = (0.5f).dp, color = LightGrey)
                        .clickable(onClick = {
                            onRandomNumber((Math.random() * 100).toInt())
                        }),
                    contentAlignment = Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (maxWidth > 480.dp) "Insert Random" else "+ Random",
                            textAlign = TextAlign.Center,
                            fontSize = if (maxWidth > 480.dp) 30.sp else 25.sp,
                            fontFamily = roboto,
                            fontWeight = Normal,
                            color = LightGrey,
                            modifier = Modifier.weight(0.7f)
                        )
                        Box(modifier = Modifier.weight(0.3f), contentAlignment = Center) {
                            Button(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(30.dp),
                                onClick = {
                                    for (i in 1..10) {
                                        onRandomNumber((Math.random() * 100).toInt())
                                    }
                                },
                                contentPadding = PaddingValues(2.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGrey),
                            ) {
                                Text(
                                    "+ 10",
                                    fontSize = 13.sp,
                                    color = LightGrey,
                                    fontFamily = roboto,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
                        .background(Grey)
                        .border(width = (0.5f).dp, color = LightGrey)
                ) {
                    TextField(
                        value = inputStr,
                        modifier = Modifier.fillMaxSize(),
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
            FlowRow {
                // Y-SPACING
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(40.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Expand,
                        title = "SPACING"
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
                                    .size(it)
                                    .clickable { onSpacingChange(+10) },
                                tint = LightBlue
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = "Decrease",
                                modifier = Modifier
                                    .weight(0.5f)
                                    .size(it)
                                    .clickable { onSpacingChange(-10) },
                                tint = LightBlue
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(40.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.LineWeight,
                        title = "WEIGHT"
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
                                    .size(it)
                                    .clickable { onWeightChange(+100) },
                                tint = LightBlue
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = "Decrease",
                                modifier = Modifier
                                    .weight(0.5f)
                                    .size(it)
                                    .clickable { onWeightChange(-100) },
                                tint = LightBlue
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .width(240.dp)
                        .height(40.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Insights,
                        title = "BALANCE"
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(it),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            ComposeMenu(
                                width = this.maxWidth,
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
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(40.dp),
                    contentAlignment = Center
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Brush,
                        title = "COLOR"
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(it),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            ComposeMenu(
                                width = this.maxWidth,
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
                        // Display a thumbnail of each theme with an onClick to update theme
                        /*
                        LazyRow(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(items = ComposableTreeTheme.getThemes(), itemContent = { theme ->
                                // .thumbnail is a composable function defined in Theme enum
                                theme.thumbnail(height = it * 0.7f, onClick = { newTheme ->
                                    onThemeChange(newTheme)
                                })
                            })
                        }

                         */
                    }
                }
            }
        }
    }
}