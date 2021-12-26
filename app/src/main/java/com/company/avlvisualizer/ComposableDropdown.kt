import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenuItem

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.avlvisualizer.ui.theme.Grey
import com.company.avlvisualizer.ui.theme.LightBlue
import com.company.avlvisualizer.ui.theme.LightGrey


@Composable
fun ComposableDropdown(
    options: List<String>,
    height: Dp,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onSelection: (String) -> Unit
) {
    var isOpen by remember {
        mutableStateOf(expanded)
    }
    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    var currSelection by remember {
        mutableStateOf(options.first())
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(height)
                .fillMaxSize()
                .clickable {
                    isOpen = !isOpen
                }
                .border(
                    width = 0.5.dp,
                    color = LightGrey
                )
                .padding(5.dp)
        ) {
            Text(
                text = currSelection,
                color = Color.White,
                fontSize = 14.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the dropdown",
                tint = LightBlue,
                modifier = Modifier
                    .scale(1f, if (isOpen) -1f else 1f) // Mirror dropdown
            )
        }

        val heightFloat = LocalDensity.current.run { height.toPx() }
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val width = this.maxWidth
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((heightFloat * 20).dp)
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0.5f, 0f)
                        rotationX = rotateX.value
                    }
                    .alpha(alpha.value)
            ) {
                for (i in 0..options.lastIndex) {
                    drawRect(
                        color = Color.Gray,
                        topLeft = Offset(0f, heightFloat * (i + 1)),
                        size = Size(width.toPx(), heightFloat)
                    )
                }
            }
        }
    }
}
