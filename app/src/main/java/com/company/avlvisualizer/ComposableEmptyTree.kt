package com.company.avlvisualizer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.LightGrey
import com.company.avlvisualizer.ui.theme.roboto

@Composable
fun ComposableEmptyTree(boxWithConstraintsScope: BoxWithConstraintsScope) {
    // Box with constraints used to access max width and max height
    // Calculate the size of the composable such that it fits within constraints
    val respWidth = (LocalDensity.current.run {
        minOf(
            boxWithConstraintsScope.maxWidth.toPx(),
            boxWithConstraintsScope.maxHeight.toPx()
        )
    } / 2f) * 0.4f

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.binarytreevisualizerapp_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(respWidth.dp.times(0.8f))
        )
        Text(
            text = "TREE IS EMPTY",
            fontSize = 25.sp,
            fontFamily = roboto,
            textAlign = TextAlign.Center,
            color = Color(232, 179, 21)
        )
        Text(
            text = "Insert a number to begin",
            fontSize = 20.sp,
            fontFamily = roboto,
            textAlign = TextAlign.Center,
            color = LightGrey
        )
    }
}