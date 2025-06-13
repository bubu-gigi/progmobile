package com.univpm.gameon.ui.components

import android.app.TimePickerDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun TimeInput(label: String, initial: String, onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    val timeParts = initial.split(":").map { it.toInt() }
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                onTimeSelected(String.format("%02d:%02d", hour, minute))
            },
            timeParts[0],
            timeParts[1],
            true
        )
    }

    OutlinedButton(onClick = { timePickerDialog.show() }) {
        Text("$label: $initial", color = Color.White)
    }
}