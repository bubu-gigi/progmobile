package com.univpm.gameon.ui.struttura

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
import com.univpm.gameon.core.generaSlotOrari
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.ui.lemonMilkFontFamily
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelezionaOrariDialog(
    campo: Campo,
    data: Date,
    onDismiss: () -> Unit,
    onOrariConfermati: (List<Pair<String, String>>) -> Unit
) {
    val giornoSettimana = Calendar.getInstance().apply { time = data }.get(Calendar.DAY_OF_WEEK)
    val giornoGameOn = if (giornoSettimana == Calendar.SUNDAY) 7 else giornoSettimana - 1 + 1
    val agenda = campo.disponibilitaSettimanale.find { it.giornoSettimana == giornoGameOn }

    val slotSelezionati = remember { mutableStateOf(setOf<Pair<String, String>>()) }

    if (agenda != null) {
        val slots = generaSlotOrari(agenda.orarioApertura, agenda.orarioChiusura)

        androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2B2B))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Seleziona orari disponibili:", color = Color.White, fontFamily = lemonMilkFontFamily)
                    Spacer(Modifier.height(8.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(slots) { slot ->
                            val isSelected = slotSelezionati.value.contains(slot)
                            Button(
                                onClick = {
                                    slotSelezionati.value =
                                        if (isSelected) slotSelezionati.value - slot else slotSelezionati.value + slot
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Color(0xFFCFFF5E) else Color(0xFF444444),
                                    contentColor = if (isSelected) Color.Black else Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("${slot.first} - ${slot.second}", color = if (isSelected) Color.Black else Color.White)
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
