package com.company.avlvisualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import com.company.avlvisualizer.ui.theme.Grey
import com.company.avlvisualizer.ui.theme.LightBlue
import com.company.avlvisualizer.ui.theme.LightGrey
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun ComposeMenu(
    width: Dp,
    menuItems: List<String>,
    menuExpandedState: Boolean,
    seletedIndex: Int,
    updateMenuExpandStatus: () -> Unit,
    onDismissMenuView: () -> Unit,
    onMenuItemclick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .wrapContentSize(Alignment.TopStart)
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),

        ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 0.5.dp,
                    color = LightGrey
                )
                .padding(5.dp)
        ) {
            Text(
                text = menuItems[seletedIndex],
                color = Color.White,
                fontSize = 14.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the dropdown",
                tint = LightBlue,
                modifier = Modifier
                    .scale(1f, if (menuExpandedState) -1f else 1f) // Mirror dropdown
            )
        }

        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = Modifier
                .width(width * 1.1f)
                    // COLOR ACTUALLY PULLED FROM SURFACE THEME!!!!
            //    .border(width = 0.5.dp, color = LightGrey)
                .clip(CutCornerShape(10.dp))

        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    onClick = {
                        onMenuItemclick(index)
                    },
                    modifier = Modifier
                        .background(Grey)
                        .clip(RectangleShape)
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = LightGrey
                    )
                }
            }
        }
    }
}