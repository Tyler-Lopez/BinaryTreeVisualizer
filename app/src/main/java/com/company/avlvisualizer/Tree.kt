package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Tree(

) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val tree = BinaryTree()
        tree.insert(50)
        for (i in 0..25) {
            tree.insert((Math.random() * 100).toInt())
        }

        val nodes = mutableListOf<BinaryNode?>()
        val offsets = mutableListOf<Offset>()
        val parentOffsets = mutableListOf<Offset>()
        tree.traversePreOrder { offset, bstNode, parentOffset ->
            parentOffsets.add(parentOffset ?: Offset(0f, 0f))
            offsets.add(offset)
            nodes.add(bstNode)
        }

        Box(
            modifier = Modifier
                .requiredWidth(2000.dp)
                .requiredHeight(2000.dp)
                .background(Color.Magenta)
                .drawBehind {
                    for (i in 0..offsets.lastIndex) {
                        drawLine(color = Color.Black, strokeWidth = 40f, start = parentOffsets[i], end = offsets[i])
                        drawCircle(color = Color.Blue, center = offsets[i], radius = 100f)

                    }
                },
            contentAlignment = Alignment.TopCenter
        ) {
        }
    }
}