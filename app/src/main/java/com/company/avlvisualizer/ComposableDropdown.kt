import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.company.avlvisualizer.ui.theme.Grey
import com.company.avlvisualizer.ui.theme.roboto


@Composable
fun ComposableDropdown(
    items: List<String>,
    expanded: Boolean,
    selectedIndex : Int,
    updateMenuExpandStatus : () -> Unit,
    onDismissMenuView : () -> Unit,
    onMenuItemClick : (Int) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissMenuView() },
        modifier = Modifier
            .background(Grey)
    ) {

        items.forEachIndexed { index, s ->
            DropdownMenuItem(
                modifier = Modifier,
                onClick = {
                    onMenuItemClick(index)
                },
                contentPadding = PaddingValues(horizontal = 5.dp),
            ) {
                // https://foso.github.io/Jetpack-Compose-Playground/material/dropdownmenu/
                Text(
                    text = s,
                    color = Color.White,
                    fontFamily = roboto
                )
            }
        }
    }
}