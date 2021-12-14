package com.company.avlvisualizer

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.times
import kotlin.math.pow
import kotlin.random.Random

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
    var scale by remember {
        mutableStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    // Used to
    var selectionInfo: MutableList<Pair<Rect, NodeComposableData>> = mutableListOf()

    Canvas(modifier = modifier
        .pointerInput(Unit) {
            // DRAG AND ZOOM
            detectTransformGestures { centroid, pan, zoom, _ ->
                val oldScale = scale // Old Scale
                scale *= zoom // New Scale
                offset = (offset + centroid / oldScale) - (centroid / scale + pan / oldScale)
            }
        }
        .graphicsLayer {
            // APPLY ZOOM
            translationX = -offset.x * scale
            translationY = -offset.y * scale
            scaleX = scale
            scaleY = scale
            transformOrigin = TransformOrigin(0f, 0f)
        }
        .background(Color.DarkGray)
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
            val nodeSize = style.nodeSize

            for (child in node.path) {
                parentPosition = Offset(xShift, yShift)
                when (child) {
                    BinaryNodeChild.LEFT -> xShift -= nodeSize * 0.07f * 2f.pow(
                        nodeHeight + 2
                    )
                    BinaryNodeChild.RIGHT -> xShift += nodeSize * 0.07f * 2f.pow(
                        nodeHeight + 2
                    )
                }
                nodeHeight -= 1
                yShift += nodeSize * 50f
            }

            center = this.center + Offset(xShift, yShift)

            selectionInfo.add(
                Pair(
                    Rect(
                        center = center,
                        radius = nodeSize
                    ),
                    node
                )
            )


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
                    center.y + (style.nodeSize / 3),
                    paint
                )
            }
        }
    }
}



