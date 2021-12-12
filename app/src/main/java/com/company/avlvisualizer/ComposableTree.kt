package com.company.avlvisualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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
    var rectList: MutableList<Rect> = mutableListOf()

    Canvas(modifier = modifier
        .pointerInput(Unit) {
            // NODE SELECT
            detectTapGestures(
                onTap = {
                    var found = false
                    for (i in 0..rectList.lastIndex) {
                        println("i is $i")
                        val rect = rectList[i]
                        if (rect.contains(it)) {
                            println("i is $i")
                            onNodeSelect("Node selected ${rectList.size}")
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

        rectList.clear()

        // Iterate through data, drawing each node
        val dataList = data.first
        val height = data.second

        var toDrawSelected: Offset? = null

        for (i in 0..dataList.lastIndex) {

            var xShift = 0f
            var yShift = 0f

            var parentPosition = Offset(0f, 0f)

            var nodeHeight = height

            val node = dataList[i]

            for (child in node.path) {
                parentPosition = Offset(xShift, yShift)
                when (child) {
                    BinaryNodeChild.LEFT -> xShift -= 50f * nodeHeight.toDouble().pow(2.0).toFloat()
                    BinaryNodeChild.RIGHT -> xShift += 50f * nodeHeight.toDouble().pow(2.0).toFloat()
                }
                nodeHeight -= 1
                yShift += 300f
            }

            center = this.center + Offset(xShift, yShift)

            val isSelected = i == selectedIndex

            rectList.add(Rect(center = center, radius = style.nodeSize))


            val color = if (isSelected) style.selectedNodeColor else style.nodeColor
            drawLine(
                color = style.nodeColor,
                start = this.center + parentPosition,
                end = center,
                strokeWidth = style.lineWidth
            )
            // Draw border circle behind circle at 1.1x size
            if (isSelected) {
                drawArc(
                    color = style.selectedNodeBorderColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = center - Offset(style.nodeSize * 1.1f, style.nodeSize * 1.1f),
                    size = Size(style.nodeSize * 2, style.nodeSize * 2).times(
                        ScaleFactor(
                            1.1f,
                            1.1f
                        )
                    )
                )
                toDrawSelected = center
            }

            drawCircle(
                center = center,
                color = color,
                radius = style.nodeSize
            )
        }

        // THIS IS A HACKY FIX to ensure the selected node is always above the line... come back to soon
        if (toDrawSelected != null) {
            drawArc(
                color = style.selectedNodeBorderColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = toDrawSelected - Offset(style.nodeSize * 1.1f, style.nodeSize * 1.1f),
                size = Size(style.nodeSize * 2, style.nodeSize * 2).times(
                    ScaleFactor(
                        1.1f,
                        1.1f
                    )
                )
            )
            drawCircle(
                center = toDrawSelected,
                color = style.selectedNodeColor,
                radius = style.nodeSize
            )
        }


    }
}

