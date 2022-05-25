package me.dannly.mirainikki.presentation.note_add.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ChipRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    individualChipModifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    elements: List<String>,
    currentSelection: Int,
    onElementSelected: (Int) -> Unit
) {
    val isCurrentlySelected: (Int) -> Boolean = {
        currentSelection == it
    }
    val unselectedColor = Color.Gray
    val selectedColor = MaterialTheme.colors.primary
    Row(horizontalArrangement = horizontalArrangement, modifier = modifier) {
        elements.forEachIndexed { index, string ->
            val currentColor by animateColorAsState(targetValue = if (isCurrentlySelected(index)) selectedColor else unselectedColor)
            Box(
                contentAlignment = Alignment.Center, modifier = individualChipModifier
                    .clickable {
                        onElementSelected(index)
                    }
                    .background(
                        color = currentColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(8.dp)
            ) {
                Text(
                    style = textStyle,
                    text = string
                )
            }
            if (index != elements.lastIndex)
                Spacer(modifier = Modifier.width(16.dp))
        }
    }
}