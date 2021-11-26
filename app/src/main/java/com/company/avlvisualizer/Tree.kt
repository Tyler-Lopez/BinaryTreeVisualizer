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
        val leftChild = BSTNode(10, 1)
        val leftleftChild = BSTNode(20, 2)
        val rightChild = BSTNode(15, 1)
        root.leftChild = leftChild
        root.rightChild = rightChild
        leftChild.leftChild = leftleftChild


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
            for (node in nodes) {
                Text(
                    text = "${node?.value ?: "null"}",
                    modifier = Modifier.offset(y = (i * 100).dp)
                    )
                i++
            }
        }
    }
}