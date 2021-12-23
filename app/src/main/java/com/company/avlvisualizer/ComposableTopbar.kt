package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
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
    onRandomNumber: (Int) -> Unit
) {
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
            Row {
                Box(modifier = Modifier
                    .weight(0.5f)
                    .padding(5.dp)
                    .background(Grey)
                    .border(width = (0.5f).dp, color = LightGrey)
                    .clickable(onClick = {
                        onRandomNumber((Math.random() * 100).toInt())
                    })
                ) {
                    Text(
                        text = "ADD RANDOM",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontFamily = roboto,
                        fontWeight = Normal,
                        color = White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Box(modifier = Modifier.weight(0.5f).background(Grey)) {
                    Text("insert")
                }
            }
            FlowRow {
                // Y-SPACING
                Box(modifier = Modifier.width(300.dp).background(Grey)) {
                    Text("box1")

                }
                // BALANCE
                Box(modifier = Modifier.width(300.dp).background(Grey)) {
                    Text("box2")

                }
                // THEME
                Box(modifier = Modifier.width(300.dp).background(Grey)) {
                    Text("box3")

                }
            }
            Row {

            }

        }

    }
}