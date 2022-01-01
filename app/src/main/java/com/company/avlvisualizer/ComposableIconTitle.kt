package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle.Companion.Normal
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.Grey
import com.company.avlvisualizer.ui.theme.LightBlue
import com.company.avlvisualizer.ui.theme.LightGrey
import com.company.avlvisualizer.ui.theme.roboto

@Composable
fun ComposableIconTitle(
    icon: ImageVector?,
    title: String?,
    content: @Composable RowScope.(Dp) -> Unit
) {
    BoxWithConstraints {
        val boxWithConstraintsScope = this
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null)
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(boxWithConstraintsScope.maxHeight.times(0.7f)),
                tint = LightGrey
            )
            if (title != null)
                Text(
                    text = "$title",
                    color = LightGrey,
                    fontFamily = roboto,
                    fontSize = 22.sp
                )
            Spacer(modifier = Modifier.padding(start=5.dp).width(2.dp).fillMaxHeight().background(LightGrey))
            content(boxWithConstraintsScope.maxHeight)
        }
    }
}