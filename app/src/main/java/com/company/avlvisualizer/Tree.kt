package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Tree(

) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val root = BSTNode(5, 0)
        root.insert(1)
        root.insert(6)
        root.insert(20)
        root.insert(69)
        root.insert(50)
        root.insert(6)


        val nodes = mutableListOf<BSTNode?>()
        root.forEachLevelOrder { nodes.add(it) }

        Box(
            modifier = Modifier
                .requiredWidth(2000.dp)
                .requiredHeight(2000.dp)
                .background(Color.Magenta),
            contentAlignment = Alignment.TopCenter
        ) {
            var i = 0
            var depth = 0
            var xShift = 0
            for (node in nodes) {
                Text(
                    text = "${node?.value ?: "null"}",
                    modifier = Modifier.offset(x = xShift.dp,y = (depth * 100).dp)
                    )
                i++
                if (
                    i == 1 || i == 3 || i == 7
                ) {
                    depth++
                    xShift = 0
                } else {
                    xShift += 100
                }
            }
        }
    }
}