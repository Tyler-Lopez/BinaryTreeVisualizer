package com.company.avlvisualizer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Node(
    element: Int,
    height: Int,
    balanceFactor: Int,
) {
    Box(
        modifier = Modifier
            .size(100.dp)

    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = element.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Card(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape),
                backgroundColor = Color.LightGray
            ) {
                Text(
                    text = balanceFactor.toString(),
                    textAlign = TextAlign.Center
                )
            }
            Card(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape),
                backgroundColor = Color.LightGray
            ) {
                Text(
                    text = balanceFactor.toString(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}