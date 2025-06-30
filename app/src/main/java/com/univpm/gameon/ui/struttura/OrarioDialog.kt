package com.univpm.gameon.ui.struttura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.univpm.gameon.data.collections.TemplateGiornaliero
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.Dropdown
import com.univpm.gameon.ui.components.TimeInput

@Composable
fun OrarioDialog(
    onOrarioAdded: (TemplateGiornaliero) -> Unit,
    onDismiss: () -> Unit,
) {
    val giorniSettimana = listOf(
        "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"
    )

    var giorno by remember { mutableStateOf(1) }
    var orarioInizio by remember { mutableStateOf("08:00") }
    var orarioFine by remember { mutableStateOf("20:00") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF333333),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Seleziona giorno e orari",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Dropdown(
                    current = giorno,
                    options = (1..7).toList(),
                    getLabel = { giorniSettimana[it - 1] },
                    label = "Giorno",
                    onSelected = { giorno = it }
                )

                TimeInput("Orario inizio", orarioInizio) { orarioInizio = it }

                TimeInput("Orario fine", orarioFine) { orarioFine = it }

                ButtonComponent(
                    text = "Salva orario",
                    onClick = {
                        onOrarioAdded(
                            TemplateGiornaliero(
                                giornoSettimana = giorno,
                                orarioApertura = orarioInizio,
                                orarioChiusura = orarioFine
                            )
                        )
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
