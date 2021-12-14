package com.company.avlvisualizer

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.times
import kotlin.math.pow

@Composable
fun ComposableTree(
    data: Pair<List<NodeComposableData>, Int>,
    modifier: Modifier = Modifier,
    style: ComposableTreeStyle = ComposableTreeStyle(),
    onNodeSelect: (String) -> Unit
) {
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var selectedIndex: Int by remember {
        mutableStateOf(-1)
    }
    // Used to
    var selectionInfo: MutableList<Pair<Rect, NodeComposableData>> = mutableListOf()

    Canvas(modifier = modifier
        .pointerInput(Unit) {
            // NODE SELECT
            detectTapGestures(
                onTap = {
                    var found = false
                    for (i in 0..selectionInfo.lastIndex) {
                        println("i is $i")
                        val rect = selectionInfo[i].first
                        if (rect.contains(it)) {
                            println("i is $i")
                            onNodeSelect("NODE Value: ${selectionInfo[i].second.value}\nHEIGHT:")
                            selectedIndex = i
                            found = true
                        }
                    }
                    if (!found) {
                        onNodeSelect("Not selected node")
                        selectedIndex = -1
                    }

                }
            )
        }
    ) {

        selectionInfo.clear()

        // Iterate through data, drawing each node
        val dataList = data.first
        val height = data.second

        for (i in 0..dataList.lastIndex) {

            var xShift = 0f
            var yShift = 0f

            var parentPosition = Offset(0f, 0f)

            var nodeHeight = height

            val node = dataList[i]

            for (child in node.path) {
                parentPosition = Offset(xShift, yShift)
                when (child) {
                    BinaryNodeChild.LEFT -> xShift -= 5f * nodeHeight.toDouble().pow(3.0).toFloat()
                    BinaryNodeChild.RIGHT -> xShift += 5f * nodeHeight.toDouble().pow(3.0)
                        .toFloat()
                }
                nodeHeight -= 1
                yShift += 1000f
            }

            center = this.center + Offset(xShift, yShift)

            selectionInfo.add(Pair(Rect(center = center, radius = style.nodeSize), node))


            drawLine(
                color = style.nodeColor,
                start = this.center + parentPosition,
                end = center,
                strokeWidth = style.lineWidth
            )
        }

        for (i in 0..selectionInfo.lastIndex) {

            val node = selectionInfo[i].second
            center = selectionInfo[i].first.center

            val isSelected = i == selectedIndex

            /*
            Temporarily commented out, this will give border to circle
            if (isSelected) {
                drawArc(
                    color = style.selectedNodeBorderColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = ,
                    size = Size(style.nodeSize * 2, style.nodeSize * 2).times(
                        ScaleFactor(
                            1.1f,
                            1.1f
                        )
                    )
                )
            }
            */

            drawCircle(
                center = center,
                color = if (isSelected) style.selectedNodeColor else style.nodeColor,
                radius = style.nodeSize
            )

            val paint = Paint()
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = style.nodeSize
            paint.color = 0xffffffff.toInt()

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "${node.value}",
                    center.x,
                    center.y + (style.nodeSize/3),
                    paint
                )
            }
        }
    }
}


