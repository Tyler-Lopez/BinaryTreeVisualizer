package com.company.avlvisualizer

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun ComposableTree(
    data: List<NodeComposableData>,
    modifier: Modifier = Modifier,
    style: ComposableTreeStyle = ComposableTreeStyle(), // Dependency Injection
    onNodeSelect: (String) -> Unit, // When a node is selected, return String to caller
) {
    // Which Node is currently tap-selected?
    var selectedIndex: Int by remember {
        mutableStateOf(-1)
    }
    // Mutable .graphicsLayer-based scaling
    var scale by remember {
        mutableStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    // List of pairs
    // .first = the position of the node
    // .second = the node information such as height, path, & value
    val nodePosInfo: MutableList<Pair<Offset, NodeComposableData>> = mutableListOf()


    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val boxWithConstraintsScope = this

        Canvas(modifier = modifier
            .width(boxWithConstraintsScope.maxWidth)
            .height(boxWithConstraintsScope.maxHeight)
            .background(Color.DarkGray)
            .pointerInput(Unit) {
                // DRAG AND ZOOM
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val oldScale = scale // Old Scale
                    scale *= zoom // New Scale
                    offset = (offset + centroid / oldScale) - (centroid / scale + pan / oldScale)
                }
            }
            .pointerInput(Unit) {
                // NODE SELECT
                detectTapGestures {
                    for (i in 0..nodePosInfo.lastIndex) {
                        val nodePos = nodePosInfo[i].first
                        val distance = sqrt(
                            (it.x - nodePos.x).pow(2) + (it.y - nodePos.y).pow(2)
                        )
                        // Click was within a node
                        if (distance <= style.nodeSize) {
                            return@detectTapGestures
                        }
                    }
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


        ) {
            // Anytime the composable is recomposed, ensure coordinate inform is cleared
            nodePosInfo.clear()

            // Height of the tree used to determine xShift offset
            val height = data[0].height

            // Iterate through each node
            for (i in 0..data.lastIndex) {

                var xShift = 0f
                var yShift = 0f

                var parentPosition = Offset(0f, 0f)
                var nodeHeight = height

                val node = data[i]
                val nodeSize = style.nodeSize

                // Calculate node position from the path
                for (child in node.path) {
                    parentPosition = Offset(xShift, yShift)
                    when (child) {
                        BinaryNodeChild.LEFT -> xShift -= nodeSize * 0.05f * 2f.pow(
                            nodeHeight + 3
                        )
                        BinaryNodeChild.RIGHT -> xShift += nodeSize * 0.05f * 2f.pow(
                            nodeHeight + 3
                        )
                    }
                    nodeHeight -= 1
                    yShift += nodeSize * style.ySpacing
                }

                val nodePos = Offset(center.x + xShift, center.y + yShift)
                val parentPos = Offset(center.x + parentPosition.x, center.y + parentPosition.y)

                // Log the position where this node should be drawn
                nodePosInfo.add(
                    Pair(
                        nodePos,
                        node
                    )
                )

                // Draw a line from this node to the parent
                // Must be done before drawing a circle for correct layering
                drawLine(
                    color = style.nodeColor,
                    start = parentPos,
                    end = nodePos,
                    strokeWidth = style.lineWidth
                )
            } // End initial iteration


            for (i in 0..nodePosInfo.lastIndex) {

                val centerPos = nodePosInfo[i].first
                val node = nodePosInfo[i].second

                val isSelected = i == selectedIndex

                // Draw Node
                drawCircle(
                    center = centerPos,
                    color = if (isSelected) style.selectedNodeColor else style.nodeColor,
                    radius = style.nodeSize,
                    style = Stroke(
                        width = if (isSelected) style.nodeSize * .1f else 0f
                    )
                )

                // Draw Text
                val paint = Paint()
                paint.textAlign = Paint.Align.CENTER
                paint.textSize = style.nodeSize
                paint.color = 0xffffffff.toInt()

                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "${node.value}",
                        centerPos.x,
                        centerPos.y + (style.nodeSize / 3),
                        paint
                    )
                }

            }
        }
    }
}



