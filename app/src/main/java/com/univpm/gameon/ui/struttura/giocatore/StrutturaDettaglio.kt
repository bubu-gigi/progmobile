package com.univpm.gameon.ui.struttura.giocatore

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.GiocatorePrenotazioniRoute
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.core.futuraBookFontFamily
import com.univpm.gameon.core.lemonMilkFontFamily
import com.univpm.gameon.core.raggruppaSlotConsecutivi
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.TemplateGiornaliero
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.struttura.CampoDatePicker
import com.univpm.gameon.viewmodels.PrenotazioneViewModel
import com.univpm.gameon.viewmodels.StruttureViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StrutturaDettaglioScreen(
    navController: NavController,
    strutturaId: String,
) {
    val viewModel: StruttureViewModel = hiltViewModel()

    val struttura by viewModel.strutturaSelezionata.collectAsState()
    val campi by viewModel.campiStruttura.collectAsState()

    LaunchedEffect(strutturaId) {
        viewModel.caricaStruttura(strutturaId)
    }

    struttura?.let {
        BackgroundScaffold {
            StrutturaDettaglioContent(
                navController = navController,
                struttura = it,
                campi = campi
            )
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Caricamento struttura...")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StrutturaDettaglioContent(
    navController: NavController,
    struttura: Struttura,
    campi: List<Campo>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = struttura.nome,
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = lemonMilkFontFamily
            )
            Spacer(Modifier.height(8.dp))
            Text("Indirizzo: ${struttura.indirizzo}", color = Color.White, fontFamily = futuraBookFontFamily)
            Text("Città: ${struttura.citta}", color = Color.White, fontFamily = futuraBookFontFamily)
            Text(
                "Sport disponibili: ${struttura.sportPraticabili.joinToString { it.label }}",
                color = Color.White,
                fontFamily = futuraBookFontFamily
            )
            Spacer(Modifier.height(20.dp))
            Text("Campi Disponibili:", fontSize = 20.sp, color = Color.White, fontFamily = lemonMilkFontFamily)
            Spacer(Modifier.height(15.dp))
        }

        items(campi) { campo ->
            CampoCard(campo, struttura.id, navController)
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CampoCard(campo: Campo, strutturaId: String, navController: NavController) {
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
            Text("Sport: ${campo.sport.label}", color = Color.White)
            Text("Terreno: ${campo.tipologiaTerreno.label}", color = Color.White)
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
                                )
                                prenotazioniViewModel.creaPrenotazione(prenotazione)
                                navController.navigate(GiocatorePrenotazioniRoute)
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
