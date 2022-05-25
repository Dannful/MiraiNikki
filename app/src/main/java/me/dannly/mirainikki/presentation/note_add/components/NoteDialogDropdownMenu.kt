package me.dannly.mirainikki.presentation.note_add.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteDialogDropdownMenu(
    items: List<String>,
    selectedText: String,
    enabled: @Composable ((String) -> Boolean)? = null,
    onSelected: (Int) -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(readOnly = true, value = selectedText, onValueChange = {}, trailingIcon = {
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }, modifier = Modifier
            .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(enabled = enabled?.invoke(s) ?: true, onClick = {
                    expanded = false
                    onSelected(index)
                }) {
                    Text(text = s)
                }
            }
        }
    }
}