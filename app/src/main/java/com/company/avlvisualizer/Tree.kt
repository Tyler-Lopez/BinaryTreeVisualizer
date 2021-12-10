package com.company.avlvisualizer

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp


@Composable
fun Tree(
    modifier: Modifier,
    nodes: List<BinaryNode?>,
    offsets: List<Offset>,
    parentOffsets: List<Offset>,
    nodeSelect: (String) -> Unit
) {


    Canvas(
        modifier = modifier
    ) {
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
                radius = 750f,
            )
            val paint = android.graphics.Paint()
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = 1250f
            paint.color = 0xffffffff.toInt()

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "${nodes[i]?.value ?: -1}",
                    offsets[i].x + 1000f,
                    offsets[i].y + 450f,
                    paint
                )
            }
        }
    }
}