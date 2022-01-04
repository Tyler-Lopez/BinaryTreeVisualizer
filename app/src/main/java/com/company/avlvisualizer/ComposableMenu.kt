package com.company.avlvisualizer

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.company.avlvisualizer.domain.Dropdownable
import com.company.avlvisualizer.ui.theme.Grey
import com.company.avlvisualizer.ui.theme.LightBlue

@Composable
fun ComposeMenu(
 //   width: Dp,
    menuItems: List<Dropdownable>,
    menuExpandedState: Boolean,
    selectedIndex: Int,
    updateMenuExpandStatus: () -> Unit,
    onDismissMenuView: () -> Unit,
    onMenuItemclick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
        //    .width(width)
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            )
            .shadow(5.dp),
        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .background(Grey)
                .padding(horizontal = 10.dp)
        ) {
            menuItems[selectedIndex].thumbnail()
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the dropdown",
                tint = LightBlue,
                modifier = Modifier
                    .size(30.dp)
                    .scale(1f, if (menuExpandedState) -1f else 1f) // Mirror dropdown
            )
        }

        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = Modifier
           //     .width(width * 0.95f)
                .clip(CutCornerShape(10.dp))

        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    onClick = {
                        onMenuItemclick(index)
                    },
                    modifier = Modifier
                       // .background(Grey)
                        .clip(RectangleShape)
                ) {
                    title.thumbnail()
                }
            }
        }
    }
}