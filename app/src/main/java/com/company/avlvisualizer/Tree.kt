package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@Composable
fun Tree(

) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        for (i in 0..5) {
            var nodeCount = 2.0.pow(i).toInt()
            Row(modifier = Modifier.requiredWidth(2000.dp).background(Color.Magenta), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                for (j in 1..nodeCount) {
                    Node(
                        element = 1,
                        height = 1,
                        balanceFactor = 1,
                    )
                }
            }
        }
    }
}