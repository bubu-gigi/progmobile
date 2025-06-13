package com.univpm.gameon.ui.struttura

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.univpm.gameon.data.collections.TemplateGiornaliero
import com.univpm.gameon.ui.components.TimeInput


@OptIn(ExperimentalMaterial3Api::class)
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
    var menuEspanso by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF333333),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Seleziona giorno e orari", color = Color.White)

                ExposedDropdownMenuBox(
                    expanded = menuEspanso,
                    onExpandedChange = { menuEspanso = !menuEspanso }
                ) {
                    OutlinedTextField(
                        value = giorniSettimana[giorno - 1],
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Giorno", color = Color.White) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuEspanso)
                        },
                        textStyle = TextStyle(color = Color.White),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            focusedBorderColor = Color.Yellow,
                            unfocusedBorderColor = Color.Yellow,
                            focusedContainerColor = Color(0xFF2B2B2B),
                            unfocusedContainerColor = Color(0xFF2B2B2B)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = menuEspanso,
                        onDismissRequest = { menuEspanso = false }
                    ) {
                        giorniSettimana.forEachIndexed { index, label ->
                            DropdownMenuItem(
                                text = { Text(label, color = Color.White) },
                                onClick = {
                                    giorno = index + 1
                                    menuEspanso = false
                                }
                            )
                        }
                    }
                }

                TimeInput("Orario inizio", orarioInizio) { orarioInizio = it }

                TimeInput("Orario fine", orarioFine) { orarioFine = it }

                Spacer(Modifier.height(10.dp))

                Button(onClick = {
                    onOrarioAdded(
                        TemplateGiornaliero(
                            giornoSettimana = giorno,
                            orarioApertura = orarioInizio,
                            orarioChiusura = orarioFine
                        )
                    )
                    onDismiss()
                }) {
                    Text("Salva orario")
                }
            }
        }
    }
}