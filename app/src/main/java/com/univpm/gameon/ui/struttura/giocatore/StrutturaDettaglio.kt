package com.univpm.gameon.ui.struttura.giocatore

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import com.univpm.gameon.R
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
    prenotazioneId: String? = null
) {
    val strutturaviewModel: StruttureViewModel = hiltViewModel()
    val prenotazioneViewModel: PrenotazioneViewModel = hiltViewModel()

    val struttura by strutturaviewModel.strutturaSelezionata.collectAsState()
    val campi by strutturaviewModel.campiStruttura.collectAsState()

    LaunchedEffect(strutturaId) {
        strutturaviewModel.caricaStruttura(strutturaId)
        prenotazioneViewModel.caricaTuttePrenotazioni()
        if (prenotazioneId != null) {
            prenotazioneViewModel.caricaPrenotazione(prenotazioneId)
        }
    }

    struttura?.let {
        BackgroundScaffold(backgroundResId = R.drawable.sfondogrigio) {
            StrutturaDettaglioContent(
                navController = navController,
                struttura = it,
                campi = campi,
                prenotazioneViewModel = prenotazioneViewModel,
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
    campi: List<Campo>,
    prenotazioneViewModel: PrenotazioneViewModel,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = struttura.nome,
                    fontSize = 26.sp,
                    fontFamily = lemonMilkFontFamily,
                    color = Color(0xFFCFFF5E)
                )
                Spacer(Modifier.height(6.dp))
                Text("Indirizzo: ${struttura.indirizzo}", color = Color.White, fontFamily = futuraBookFontFamily)
                Text("Città: ${struttura.citta}", color = Color.White, fontFamily = futuraBookFontFamily)
                Text(
                    "Sport disponibili: ${struttura.sportPraticabili.joinToString { it.label }}",
                    color = Color.White,
                    fontFamily = futuraBookFontFamily
                )
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "Campi Disponibili:",
                fontSize = 22.sp,
                fontFamily = lemonMilkFontFamily,
                color = Color(0xFFCFFF5E)
            )
            Spacer(Modifier.height(14.dp))
        }

        items(campi) { campo ->
            CampoCard(campo, struttura.id, struttura.nome, navController, prenotazioneViewModel)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CampoCard(campo: Campo, strutturaId: String, strutturaNome: String, navController: NavController, prenotazioneViewModel: PrenotazioneViewModel) {
    val prenotazioni by prenotazioneViewModel.prenotazioni.collectAsState()
    var dataSelezionata by remember { mutableStateOf<Date?>(null) }
    var showOrariDialog by remember { mutableStateOf(false) }
    var orariSelezionati by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    val prenotazioneSelezionata by prenotazioneViewModel.prenotazione.collectAsState()

    LaunchedEffect(prenotazioneSelezionata) {
        prenotazioneSelezionata?.let { pren ->
            if (pren.campoId == campo.id) {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dataSelezionata = formatter.parse(pren.data)
                orariSelezionati = listOf(pren.orarioInizio to pren.orarioFine)
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color(0xFFE36BE0)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2B2B)) // più contrasto sullo sfondo
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🏷️  ${campo.nomeCampo}", fontSize = 20.sp, color = Color(0xFFCFFF5E), fontFamily = lemonMilkFontFamily)
            Spacer(Modifier.height(10.dp))

            Text("Sport: ${campo.sport.label}", color = Color.White, fontFamily = futuraBookFontFamily)
            Text("Terreno: ${campo.tipologiaTerreno.label}", color = Color.White, fontFamily = futuraBookFontFamily)
            Text("Prezzo orario: €${campo.prezzoOrario}", color = Color.White, fontFamily = futuraBookFontFamily)
            Text("N. giocatori: ${campo.numeroGiocatori}", color = Color.White, fontFamily = futuraBookFontFamily)
            Text("Spogliatoi: ${if (campo.spogliatoi) "Sì" else "No"}", color = Color.White, fontFamily = futuraBookFontFamily)

            Spacer(Modifier.height(15.dp))
            Text("📆 Orari disponibili:", fontSize = 16.sp, color = Color(0xFFCFFF5E), fontFamily = futuraBookFontFamily)

            Spacer(Modifier.height(4.dp))
            campo.disponibilitaSettimanale.forEach { giorno ->
                GiornoDisponibilitaRow(giorno)
            }

            Spacer(Modifier.height(12.dp))

            dataSelezionata?.let {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                Spacer(Modifier.height(8.dp))
                Text("📅 Data selezionata: ${sdf.format(it)}", color = Color.White, fontFamily = futuraBookFontFamily)
            }

            if (showOrariDialog && dataSelezionata != null) {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dataString = sdf.format(dataSelezionata)

                val prenotazioniOccupate = prenotazioni.filter {
                    it.campoId == campo.id && it.data == dataString
                }

                SelezionaOrariDialog(
                    campo = campo,
                    data = dataSelezionata!!,
                    prenotazioniOccupate = prenotazioniOccupate,
                    onDismiss = { showOrariDialog = false },
                    onOrariConfermati = { selezionati ->
                        orariSelezionati = selezionati
                        showOrariDialog = false
                    }
                )

            }

            if (orariSelezionati.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    "⏱️ Orari selezionati:",
                    color = Color.White,
                    fontFamily = futuraBookFontFamily
                )
                val raggruppati = raggruppaSlotConsecutivi(orariSelezionati)
                raggruppati.forEach { (inizio, fine) ->
                    Text("- $inizio → $fine", color = Color.White)
                }
                Spacer(Modifier.height(12.dp))
            }


                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CampoDatePicker(
                        campo = campo,
                        onDateSelected = { data ->
                            dataSelezionata = data
                            showOrariDialog = true
                        },
                        textButton = if (dataSelezionata == null) "Scegli Data" else "Cambia Data"
                    )

                    if(dataSelezionata != null && orariSelezionati.isNotEmpty()) {
                        val isModifica = prenotazioneSelezionata?.let { it.campoId == campo.id } ?: false

                        Button(
                            onClick = {
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val dataString = sdf.format(dataSelezionata)
                                val slotRaggruppati = raggruppaSlotConsecutivi(orariSelezionati)

                                if (isModifica && prenotazioneSelezionata != null) {
                                    prenotazioneViewModel.annullaPrenotazione(prenotazioneSelezionata!!.id)
                                }

                                slotRaggruppati.forEach { (inizio, fine) ->
                                    val prenotazione = Prenotazione(
                                        id = "-1",
                                        userId = UserSessionManager.userId ?: "",
                                        strutturaId = strutturaId,
                                        campoId = campo.id,
                                        strutturaNome = strutturaNome,
                                        campoNome = campo.nomeCampo,
                                        data = dataString,
                                        orarioInizio = inizio,
                                        orarioFine = fine
                                    )
                                    prenotazioneViewModel.creaPrenotazione(prenotazione)
                                }

                                navController.navigate(GiocatorePrenotazioniRoute)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCFFF5E))
                        ) {
                            Text(if (isModifica) "Salva modifiche" else "Prenota", color = Color.Black)
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
