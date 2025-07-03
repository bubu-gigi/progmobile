package com.univpm.gameon.ui.struttura.giocatore

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.ui.components.ButtonComponent
import java.util.Calendar
import java.util.Date

@Composable
fun CampoDatePicker(
    campo: Campo,
    onDateSelected: (Date) -> Unit,
    textButton: String? = "Scegli una data"
) {
    val context = LocalContext.current
    val today = Calendar.getInstance()
    val giorniDisponibili = campo.disponibilitaSettimanale.map { it.giornoSettimana }

    ButtonComponent(text = textButton!!, onClick = {
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selected = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                val calendarDayOfWeek = selected.get(Calendar.DAY_OF_WEEK)

                if (giorniDisponibili.contains(calendarDayOfWeek)) {
                    onDateSelected(selected.time)
                } else {
                    Toast.makeText(
                        context,
                        "Giorno non disponibile per questo campo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.minDate = System.currentTimeMillis()
        datePicker.show()
    })
}
