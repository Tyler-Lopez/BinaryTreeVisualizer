package com.company.avlvisualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ComposableTree(
    modifier: Modifier = Modifier,
    style: ComposableTreeStyle = ComposableTreeStyle(),
    onNodeSelect: (String) -> Unit
) {
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var isSelected by remember {
        mutableStateOf(false)
    }
    var rectList: MutableList<Rect> = mutableListOf()
    Canvas(modifier = modifier
        .pointerInput(Unit) {
            // NODE SELECT
            detectTapGestures(
                onTap = {
                        var found = false
                        for (rect in rectList) {
                            if (rect.contains(it)) {
                                onNodeSelect("Node selected")
                                isSelected = true
                                found = true
                            }
                        }
                        if (!found) {
                            onNodeSelect("Not selected node")
                            isSelected = false
                        }

                }
            )
        }
        ) {
        center = this.center
        rectList.add(Rect(center = center, radius = 10f))
        drawCircle(
            center = center,
            color = if (isSelected) style.selectedNodeColor else style.nodeColor,
            radius = 10f
        )
    }
}