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
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun ComposableTree(
    data: List<NodeComposableData>,
    modifier: Modifier = Modifier,
    style: ComposableTreeStyle = ComposableTreeStyle(), // Dependency Injection
    onNodeSelect: (Int?) -> Unit, // When a node is selected, return String to caller
) {
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
    // Important to be mutable so it is regenerated upon new node addition
    val nodePosInfo by remember {
        mutableStateOf(mutableStateListOf<Pair<Offset, NodeComposableData>>())
    }


    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        var center by remember {
            mutableStateOf(
                Offset(
                    constraints.maxWidth / 2f,
                    constraints.maxHeight / 2f
                )
            )
        }

        Canvas(modifier = modifier
            .width(constraints.maxWidth.dp)
            .height(constraints.maxHeight.dp)
            .pointerInput(Unit) {
                // NODE SELECT
                detectTapGestures {
                    // Temporarily commented out until full function exists

                    // Define the click with respect to the offset relative to center of the screen
                    val clickOffset = Offset(
                        it.x - center.x,
                        it.y - center.y
                    )
                    // Add the actual center back
                    val adjClick = Offset(
                        (clickOffset.x / scale) + (center.x) + offset.x * scale,
                        (clickOffset.y / scale) + (center.y) + offset.y * scale,
                    )

                    for (i in 0..nodePosInfo.lastIndex) {
                        val nodePos = Offset(
                            nodePosInfo[i].first.x * scale,
                            nodePosInfo[i].first.y * scale
                        )
                        val node = nodePosInfo[i].second
                        val distance = sqrt(
                            (adjClick.x - nodePos.x).pow(2) + (adjClick.y - nodePos.y).pow(2)
                        )
                        // Click was within a node
                        if (distance <= (style.nodeSize * scale * 2)) {
                            onNodeSelect(i)
                            return@detectTapGestures
                        }
                    }
                    onNodeSelect(null)

                }
            }
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


        ) {
            // Anytime the composable is recomposed, ensure coordinate inform is cleared
            nodePosInfo.clear()

            // Height of the tree used to determine xShift offset
            val height = data[0].height

            val nodeSize = style.nodeSize
            val lineWidth = style.lineWidth

            // Iterate through each node
            for (i in 0..data.lastIndex) {

                var xShift = 0f
                var yShift = 0f

                var parentPosition = Offset(0f, 0f)
                var nodeHeight = height

                val node = data[i]


                // Calculate node position from the path
                for (child in node.path) {
                    parentPosition = Offset(xShift, yShift)
                    when (child) {
                        BinaryNodeChild.LEFT -> xShift -= ((nodeSize * 0.2f) + 80) * 0.05f * 2f.pow(
                            nodeHeight + 4
                        )
                        BinaryNodeChild.RIGHT -> xShift += ((nodeSize * 0.2f) + 80) * 0.05f * 2f.pow(
                            nodeHeight + 4
                        )
                    }
                    nodeHeight -= 1
                    yShift += 100f * style.ySpacing
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
                if (i != 0) {
                    drawLine(
                        color = style.theme.lineColor,
                        start = parentPos,
                        end = nodePos,
                        strokeWidth = lineWidth
                    )
                }
            } // End initial iteration


            for (i in 0..nodePosInfo.lastIndex) {

                val centerPos = nodePosInfo[i].first
                val node = nodePosInfo[i].second

                // TEMPORARY ADD SELECTED LOGIC HERE
                val isSelected = false

                // Draw Node and border if selected
                drawCircle(
                    center = centerPos,
                    color = if (isSelected) style.theme.selectedNodeColor else style.theme.nodeColor,
                    radius = if (isSelected) nodeSize * 1.3f else nodeSize
                )

                // Draw Text
                val paint = Paint()
                paint.textAlign = Paint.Align.CENTER
                paint.textSize = if (isSelected) nodeSize * 1.2f else nodeSize
                paint.color = 0xffffffff.toInt()

                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "${node.value}",
                        centerPos.x,
                        centerPos.y + (nodeSize / 2.5f),
                        paint
                    )
                }

            }
        }
    }
}



