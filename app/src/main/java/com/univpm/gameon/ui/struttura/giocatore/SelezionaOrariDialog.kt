package com.univpm.gameon.ui.struttura.giocatore

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.univpm.gameon.core.generaSlotOrari
import com.univpm.gameon.core.lemonMilkFontFamily
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.ui.components.CustomText
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelezionaOrariDialog(
    campo: Campo,
    data: Date,
    prenotazioniOccupate: List<Prenotazione>,
    onDismiss: () -> Unit,
    onOrariConfermati: (List<Pair<String, String>>) -> Unit
) {
    val giornoSettimana = Calendar.getInstance().apply { time = data }.get(Calendar.DAY_OF_WEEK)
    val agenda = campo.disponibilitaSettimanale.find { it.giornoSettimana == giornoSettimana }

    val slotSelezionati = remember { mutableStateOf(setOf<Pair<String, String>>()) }

    if (agenda != null) {
        val slots = generaSlotOrari(agenda.orarioApertura, agenda.orarioChiusura)

        val slotIsOccupied: (Pair<String, String>) -> Boolean = { slot ->
            prenotazioniOccupate.any { pren ->
                !(pren.orarioFine <= slot.first || pren.orarioInizio >= slot.second)
            }
        }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2B2B))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CustomText(
                        text = "Seleziona orari disponibili:",
                        color = Color.White,
                        fontFamily = lemonMilkFontFamily
                    )
                    Spacer(Modifier.height(8.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(slots) { slot ->
                            val isOccupied = slotIsOccupied(slot)
                            val isSelected = slotSelezionati.value.contains(slot)

                            Button(
                                onClick = {
                                    if (!isOccupied) {
                                        slotSelezionati.value = if (isSelected)
                                            slotSelezionati.value - slot
                                        else
                                            slotSelezionati.value + slot
                                    }
                                },
                                enabled = !isOccupied,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = when {
                                        isOccupied -> Color(0xFFFF6B6B)
                                        isSelected -> Color(0xFFCFFF5E)
                                        else -> Color(0xFF444444)
                                    },
                                    contentColor = when {
                                        isOccupied -> Color.White
                                        isSelected -> Color.Black
                                        else -> Color.White
                                    }
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("${slot.first} - ${slot.second}")
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = { onOrariConfermati(slotSelezionati.value.toList()) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Conferma", color = Color.Black)
                    }
                }
            }
        }
    } else {
        onDismiss()
    }
}
