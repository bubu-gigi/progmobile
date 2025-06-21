package com.univpm.gameon.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.data.collections.enums.Sport
import com.univpm.gameon.data.collections.enums.TipologiaTerreno
import.com.univpm.gameon.data.collections.Campo.TemplateGiornaliero
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun CampoPrenotazioni(
    campo: Campo,
    prenotazioni: List<Prenotazione>,
    onSlotSelezionato: (Pair<LocalTime, LocalTime>) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDateMillis = datePickerState.selectedDateMillis
    val selectedDate = selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
    val selectedLocalDate = selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Seleziona una data disponibile", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Data prenotazione") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text("Conferma")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Annulla")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false
                )
            }
        }

        selectedLocalDate?.let { giorno ->
            var durataOre by remember { mutableStateOf(1) }
            val slots = generaSlotDisponibili(campo, giorno, prenotazioni, durataSlot = durataOre * 60)
            if (slots.isEmpty()) {
                Text("Nessuno slot disponibile per questa data", color = MaterialTheme.colorScheme.error)
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Durata (ore):", style = MaterialTheme.typography.titleMedium)
                    Row {
                        (1..4).forEach {
                            TextButton(onClick = { durataOre = it }) {
                                Text("\$it", color = if (durataOre == it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Seleziona uno slot:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    slots.forEach { slot ->
                        Button(
                            onClick = { onSlotSelezionato(slot) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(text = "${slot.first} - ${slot.second}")
                        }
                    }
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@RequiresApi(Build.VERSION_CODES.O)
fun generaSlotDisponibili(
    campo: Campo,
    giorno: LocalDate,
    prenotazioni: List<Prenotazione>,
    durataSlot: Int = 60
): List<Pair<LocalTime, LocalTime>> {
    val giornoSettimana = giorno.dayOfWeek.value
    val disponibilita = campo.disponibilitaSettimanale.find {
        it.giornoSettimana == giornoSettimana
    } ?: return emptyList()

    val apertura = try {
        LocalTime.parse(disponibilita.orarioApertura)
    } catch (e: Exception) {
        return emptyList()
    }

    val chiusura = try {
        LocalTime.parse(disponibilita.orarioChiusura)
    } catch (e: Exception) {
        return emptyList()
    }

    // Converti prenotazioni esistenti in intervalli occupati
    val occupati = prenotazioni
        .filter { it.data == giorno.toString() }
        .mapNotNull { pren ->
            try {
                val start = LocalTime.parse(pren.orarioInizio)
                val end = LocalTime.parse(pren.orarioFine)
                start to end
            } catch (e: Exception) {
                null
            }
        }

    // Genera gli slot disponibili
    val disponibili = mutableListOf<Pair<LocalTime, LocalTime>>()
    var orario = apertura

    while (orario.plusMinutes(durataSlot.toLong()) <= chiusura) {
        val fineSlot = orario.plusMinutes(durataSlot.toLong())

        val èSovrapposto = occupati.any { (occupatoInizio, occupatoFine) ->
            orario < occupatoFine && fineSlot > occupatoInizio
        }

        if (!èSovrapposto) {
            disponibili.add(orario to fineSlot)
        }

        orario = orario.plusMinutes(durataSlot.toLong())
    }

    return disponibili
}

@Preview(showBackground = true)
@Composable
fun CampoPrenotazioniPreview() {
    // Mock Campo con le classi
    val mockCampo = Campo(
        id = "campo1",
        nomeCampo = "Campo Tennis A",
        sport = Sport.CALCIO5, // Usando il valore di default
        tipologiaTerreno = TipologiaTerreno.ERBA_SINTETICA, // Usando il valore di default
        prezzoOrario = 25.0,
        numeroGiocatori = 10,
        spogliatoi = true,
        disponibilitaSettimanale = listOf(
            TemplateGiornaliero(
                giornoSettimana = 1, // Lunedì
                orarioApertura = "09:00",
                orarioChiusura = "22:00"
            ),
            TemplateGiornaliero(
                giornoSettimana = 2, // Martedì
                orarioApertura = "09:00",
                orarioChiusura = "22:00"
            ),
            TemplateGiornaliero(
                giornoSettimana = 6, // Sabato
                orarioApertura = "08:00",
                orarioChiusura = "23:00"
            )
        )
    )

    // Mock Prenotazioni con le tue classi reali
    val mockPrenotazioni = listOf(
        Prenotazione(
            id = "pren1",
            userId = "user1",
            strutturaId = "strutt1",
            campoId = "campo1",
            data = "2025-06-21",
            orarioInizio = "10:00",
            orarioFine = "11:00",
            pubblica = false
        ),
        Prenotazione(
            id = "pren2",
            userId = "user2",
            strutturaId = "strutt1",
            campoId = "campo1",
            data = "2025-06-21",
            orarioInizio = "14:00",
            orarioFine = "16:00",
            pubblica = true
        )
    )

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CampoPrenotazioni(
                    campo = mockCampo,
                    prenotazioni = mockPrenotazioni,
                    onSlotSelezionato = { slot ->
                        // Mock callback per la preview
                        println("Slot selezionato: ${slot.first} - ${slot.second}")
                    }
                )
            } else {
                Text("Richiede Android API 26+")
            }
        }
    }
}
