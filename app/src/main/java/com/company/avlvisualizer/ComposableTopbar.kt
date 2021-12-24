package com.company.avlvisualizer

import ComposableDropdown
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ComposableTopBar(
    message: String,
    onSpacingChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    onBalanceChange: () -> Unit,
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
    var expanded by remember {
        mutableStateOf(false)
    }
    val items = listOf("Not Balanced", "AVL Balanced")
    var selectedIndex by remember {
        mutableStateOf(0)
    }


    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth()
        .shadow(5.dp)
        .background(DarkGrey)
        .drawBehind {
            drawLine(
                color = LightGrey,
                strokeWidth = 5f,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height)
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
                    Text(
                        text = if (maxWidth > 480.dp) "Insert Random" else "+ Random",
                        fontSize = if (maxWidth > 480.dp) 30.sp else 25.sp,
                        fontFamily = roboto,
                        fontWeight = Normal,
                        color = LightGrey,
                    )

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
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Insights,
                        title = "BALANCE"
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = items[selectedIndex],
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Grey)
                                    .padding(2.dp)
                                    .clickable(onClick = { expanded = true }),
                                color = LightBlue
                            )
                            ComposableDropdown(
                                items = items,
                                expanded = expanded,
                                selectedIndex = selectedIndex,
                                updateMenuExpandStatus = { expanded = true },
                                onDismissMenuView = { expanded = false },
                                onMenuItemClick = { selectedIndex = it }
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .width(360.dp)
                ) {
                    ComposableIconTitle(
                        icon = Icons.Default.Brush,
                        title = "THEME"
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            Button(
                                modifier = Modifier
                                    .height(it)
                                    .weight(0.5f)
                                    .padding(start = 5.dp)
                                    .padding(vertical = 5.dp)
                                    .border(width = 1.dp, color = LightGrey),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Blue),
                                onClick = { onThemeChange(ComposableTreeTheme.BLUE) }
                            ) {}
                            Button(
                                modifier = Modifier
                                    .height(it)
                                    .weight(0.5f)
                                    .padding(start = 5.dp)
                                    .padding(vertical = 5.dp)
                                    .border(width = 1.dp, color = LightGrey),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Red),
                                onClick = { onThemeChange(ComposableTreeTheme.RED) }
                            ) {}
                            Button(
                                modifier = Modifier
                                    .height(it)
                                    .weight(0.5f)
                                    .padding(start = 5.dp)
                                    .padding(vertical = 5.dp)
                                    .border(width = 1.dp, color = LightGrey),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Green),
                                onClick = { onThemeChange(ComposableTreeTheme.GREEN) }
                            ) {}
                            Button(
                                modifier = Modifier
                                    .height(it)
                                    .weight(0.5f)
                                    .padding(start = 5.dp)
                                    .padding(vertical = 5.dp)
                                    .border(width = 1.dp, color = LightGrey),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Purple),
                                onClick = { onThemeChange(ComposableTreeTheme.PURPLE) }
                            ) {}
                        }
                    }
                }
            }
            Row {

            }

        }

    }
}