package com.univpm.gameon.ui.struttura

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.core.raggruppaSlotConsecutivi
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.TemplateGiornaliero
import com.univpm.gameon.ui.futuraBookFontFamily
import com.univpm.gameon.ui.lemonMilkFontFamily
import com.univpm.gameon.viewmodels.PrenotazioneViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StrutturaDettaglioScreen(
    navController: NavController,
    struttura: Struttura,
    campi: List<Campo>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = struttura.nome,
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = lemonMilkFontFamily
        )
        Text("Indirizzo: ${struttura.indirizzo}", color = Color.White, fontFamily = futuraBookFontFamily)
        Text("Città: ${struttura.citta}", color = Color.White, fontFamily = futuraBookFontFamily)
        Text(
            "Sport disponibili: ${struttura.sportPraticabili.joinToString { it.name }}",
            color = Color.White,
            fontFamily = futuraBookFontFamily
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Campi Disponibili:", fontSize = 20.sp, color = Color.White, fontFamily = lemonMilkFontFamily)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(campi) { campo ->
                CampoCard(campo, struttura.id)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CampoCard(campo: Campo, strutturaId: String) {
    val prenotazioniViewModel: PrenotazioneViewModel = hiltViewModel()

    var dataSelezionata by remember { mutableStateOf<Date?>(null) }
    var showOrariDialog by remember { mutableStateOf(false) }
    var orariSelezionati by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color(0xFFE36BE0)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nome: ${campo.nomeCampo}", color = Color.White)
            Text("Sport: ${campo.sport}", color = Color.White)
            Text("Terreno: ${campo.tipologiaTerreno}", color = Color.White)
            Text("Prezzo orario: €${campo.prezzoOrario}", color = Color.White)
            Text("N. giocatori: ${campo.numeroGiocatori}", color = Color.White)
            Text("Spogliatoi: ${if (campo.spogliatoi) "Sì" else "No"}", color = Color.White)

            Spacer(Modifier.height(8.dp))

            Text("Orari disponibili:", color = Color.White)
            campo.disponibilitaSettimanale.forEach { giorno ->
                GiornoDisponibilitaRow(giorno)
            }

            Spacer(Modifier.height(8.dp))

            CampoDatePicker(
                campo = campo,
                onDateSelected = { data ->
                    dataSelezionata = data
                    showOrariDialog = true
                }
            )

            dataSelezionata?.let {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                Spacer(Modifier.height(8.dp))
                Text("Data selezionata: ${sdf.format(it)}", color = Color.White)
            }

            if (showOrariDialog && dataSelezionata != null) {
                SelezionaOrariDialog(
                    campo = campo,
                    data = dataSelezionata!!,
                    onDismiss = { showOrariDialog = false },
                    onOrariConfermati = { selezionati ->
                        orariSelezionati = selezionati
                        showOrariDialog = false
                    }
                )
            }

            if (orariSelezionati.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text("Orari selezionati:", color = Color.White)
                orariSelezionati.forEach { slot ->
                    Text("${slot.first} - ${slot.second}", color = Color.White)
                }

                if (dataSelezionata != null) {
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val dataString = sdf.format(dataSelezionata)

                            val prenotazioniRaggruppate = raggruppaSlotConsecutivi(orariSelezionati)

                            prenotazioniRaggruppate.forEach { slot ->
                                val prenotazione = Prenotazione(
                                    userId = UserSessionManager.userId ?: "",
                                    strutturaId = strutturaId,
                                    campoId = campo.id,
                                    data = dataString,
                                    orarioInizio = slot.first,
                                    orarioFine = slot.second,
                                    pubblica = false
                                )
                                prenotazioniViewModel.creaPrenotazione(prenotazione)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Prenota", color = Color.Black)
                    }
                }
            }

        }
    }
}


@Composable
fun GiornoDisponibilitaRow(giorno: TemplateGiornaliero) {
    val giorniSettimana = listOf("Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica")
    val nomeGiorno = giorniSettimana.getOrNull(giorno.giornoSettimana - 1) ?: "?"

    Text("$nomeGiorno: ${giorno.orarioApertura} - ${giorno.orarioChiusura}", color = Color.White)
}
