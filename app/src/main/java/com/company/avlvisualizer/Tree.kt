package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
        tree.insert(69)
        tree.insert(30)
        tree.insert(100)
        tree.insert(150)
        tree.insert(2)
        tree.insert(31)
        tree.insert(420)


        val nodes = mutableListOf<BinaryNode?>()
        val offsets = mutableListOf<Offset>()
        tree.traversePreOrder { offset, bstNode ->
            offsets.add(offset)
            nodes.add(bstNode)
        }

        Box(
            modifier = Modifier
                .requiredWidth(2000.dp)
                .requiredHeight(2000.dp)
                .background(Color.Magenta),
            contentAlignment = Alignment.TopCenter
        ) {
            for (i in 0..nodes.lastIndex) {
                val currNode = nodes[i]
                val currOffset = offsets[i]
                Text(
                    text = currNode?.value.toString(),
                    modifier = Modifier.offset(x = currOffset.x.dp, y = currOffset.y.dp)
                )
            }
        }
    }
}