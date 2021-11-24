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
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val root = BSTNode(5)
        val leftChild = BSTNode(10)
        val leftleftChild = BSTNode(20)
        val rightChild = BSTNode(15)
        root.leftChild = leftChild
        root.rightChild = rightChild
        leftChild.leftChild = leftleftChild


        val levelTraverse = mutableListOf<Int>()
        root.forEachLevelOrder { levelTraverse.add(it) }


            Column(
                modifier = Modifier
                    .requiredWidth(2000.dp)
                    .requiredHeight(2000.dp)
                    .background(Color.Magenta),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in 0..levelTraverse.lastIndex) {
                    Node(
                        element = levelTraverse[i],
                        height = 1,
                        balanceFactor = 1,
                    )
            }
        }
    }
}