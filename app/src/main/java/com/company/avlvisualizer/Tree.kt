package com.company.avlvisualizer

import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
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
        for (i in 0..200) {
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
                .width(2000.dp)
                .height(2000.dp)
                .drawBehind {
                    for (i in 0..offsets.lastIndex) {
                        drawLine(
                            color = Color(217, 25, 255),
                            strokeWidth = 1000f,
                            start = Offset(parentOffsets[i].x + 1000, parentOffsets[i].y),
                            end = Offset(offsets[i].x + 1000, offsets[i].y)
                        )
                    }
                    // Add circles on top
                    for (i in 0..offsets.lastIndex) {
                        drawCircle(
                            color = Color(191, 0, 230),
                            center = Offset(offsets[i].x + 1000f, offsets[i].y),
                            radius = 750f
                        )
                        scale(150f, Offset(offsets[i].x + 1000f, offsets[i].y)) {
                                drawIntoCanvas {
                                    it.nativeCanvas.drawText(
                                        "${nodes[i]?.value ?: -1}",
                                        offsets[i].x + 1005f,
                                        offsets[i].y,
                                        Paint()
                                    )
                                }
                        }
                    }
                },
            contentAlignment = Alignment.TopCenter
        ) {
        }
    }
}