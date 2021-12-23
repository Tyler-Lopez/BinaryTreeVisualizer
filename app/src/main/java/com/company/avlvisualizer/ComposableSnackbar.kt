package com.company.avlvisualizer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.roboto

@Composable
fun ComposableSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text(
                        text = data.message,
                        textAlign = TextAlign.Center,
                        fontFamily = roboto,
                        fontWeight = Normal,
                        fontSize = 35.sp
                    )
                },
            )
        },
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(width = 1.dp, Color.LightGray)
            .shadow(5.dp)
            .wrapContentHeight(Alignment.Bottom)
    )
}