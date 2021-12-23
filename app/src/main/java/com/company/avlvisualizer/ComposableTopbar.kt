package com.company.avlvisualizer

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.DarkGrey
import com.company.avlvisualizer.ui.theme.Grey
import com.company.avlvisualizer.ui.theme.LightGrey
import com.company.avlvisualizer.ui.theme.roboto
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ComposableTopBar(
    message: String,
    onSpacingChange: (Int) -> Unit,
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
                        text = "Add Random",
                        fontSize = 35.sp,
                        fontFamily = roboto,
                        fontWeight = Bold,
                        color = White,
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
                        placeholder = {
                            Text(text = "(0 - 999)")
                        }
                    )
                }
            }
            FlowRow {
                // Y-SPACING
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .background(Grey)
                ) {
                    Text("box1")

                }
                // BALANCE
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .background(Grey)
                ) {
                    Text("box2")

                }
                // THEME
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .background(Grey)
                ) {
                    Text("box3")

                }
            }
            Row {

            }

        }

    }
}