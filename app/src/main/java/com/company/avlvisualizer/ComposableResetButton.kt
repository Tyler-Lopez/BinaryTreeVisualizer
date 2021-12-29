package com.company.avlvisualizer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.company.avlvisualizer.ui.theme.DarkGrey
import com.company.avlvisualizer.ui.theme.Red

@Composable
fun ComposableResetButton(modifier: Modifier, onReset: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = {
            onReset()
        },
        contentPadding = PaddingValues(1.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = DarkGrey),
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Reset Tree and Setitngs",
            modifier = Modifier.fillMaxSize(),
            tint = Red
        )
    }
}