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

@Composable
fun ComposableTree(
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

        for (i in 0..3) {

            center = this.center + Offset(0f, 400f * i)
            val isSelected = i == selectedIndex

            rectList.add(Rect(center = center, radius = style.nodeSize))


            val color = if (isSelected) style.selectedNodeColor else style.nodeColor

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
            }

            drawCircle(
                center = center,
                color = color,
                radius = style.nodeSize
            )
        }
    }
}

