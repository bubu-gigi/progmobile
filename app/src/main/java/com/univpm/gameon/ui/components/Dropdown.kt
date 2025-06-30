package com.univpm.gameon.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.univpm.gameon.core.futuraBookFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Dropdown(
    current: T,
    options: List<T>,
    getLabel: (T) -> String,
    label: String,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = getLabel(current),
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = label,
                    color = Color.White,
                    fontFamily = futuraBookFontFamily
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE36BE0),
                unfocusedBorderColor = Color(0xFFE36BE0),
                focusedLabelColor = Color(0xFFE36BE0),
                unfocusedLabelColor = Color(0xFFE36BE0),
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            getLabel(option),
                            color = Color.Black,
                            fontFamily = futuraBookFontFamily
                        )
                    },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
