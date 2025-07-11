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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.univpm.gameon.ui.components.CustomText
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
    val strutturaViewModel: StruttureViewModel = hiltViewModel()
    val prenotazioneViewModel: PrenotazioneViewModel = hiltViewModel()

    val struttura by strutturaViewModel.strutturaSelezionata.collectAsState()
    val campi by strutturaViewModel.campiStruttura.collectAsState()

    LaunchedEffect(strutturaId) {
        strutturaViewModel.caricaStruttura(strutturaId)
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
                strutturaViewModel = strutturaViewModel,
            )
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CustomText(text = "Caricamento struttura...")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StrutturaDettaglioContent(
    navController: NavController,
    struttura: Struttura,
    campi: List<Campo>,
    prenotazioneViewModel: PrenotazioneViewModel,
    strutturaViewModel: StruttureViewModel,
) {
    var isPreferito by remember { mutableStateOf(UserSessionManager.getCurrentUser()?.preferiti?.contains(struttura.id) == true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CustomText(
                        text = struttura.nome,
                        fontSize = 26.sp,
                        fontFamily = lemonMilkFontFamily,
                        color = Color(0xFFCFFF5E)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            strutturaViewModel.modificaPreferiti(struttura.id)
                            isPreferito = !isPreferito
                        }
                    ) {
                        Icon(
                            imageVector = if (isPreferito) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Preferito",
                            tint = if (isPreferito) Color.Yellow else Color.White
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))
                CustomText(text = "Indirizzo: ${struttura.indirizzo}", color = Color.White, fontFamily = futuraBookFontFamily)
                CustomText(text = "Città: ${struttura.citta}", color = Color.White, fontFamily = futuraBookFontFamily)
                CustomText(
                    text = "Sport disponibili: ${struttura.sportPraticabili.joinToString { it.label }}",
                    color = Color.White,
                    fontFamily = futuraBookFontFamily
                )
            }

            Spacer(Modifier.height(16.dp))

            CustomText(
                text = "Campi Disponibili:",
                fontSize = 22.sp,
                fontFamily = lemonMilkFontFamily,
                color = Color(0xFFCFFF5E)
            )
            Spacer(Modifier.height(14.dp))
        }
        if (campi.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                CustomText(
                    text = "Nessun campo disponibile per questa struttura.",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = futuraBookFontFamily
                )
            }
        } else {
            items(campi) { campo ->
                CampoCard(
                    campo = campo,
                    strutturaId = struttura.id,
                    strutturaNome = struttura.nome,
                    navController = navController,
                    prenotazioneViewModel = prenotazioneViewModel
                )
            }
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
            CustomText(text = "🏷️  ${campo.nomeCampo}", fontSize = 20.sp, color = Color(0xFFCFFF5E), fontFamily = lemonMilkFontFamily)
            Spacer(Modifier.height(10.dp))

            CustomText(text = "Sport: ${campo.sport.label}", color = Color.White, fontFamily = futuraBookFontFamily)
            CustomText(text = "Terreno: ${campo.tipologiaTerreno.label}", color = Color.White, fontFamily = futuraBookFontFamily)
            CustomText(text = "Prezzo orario: €${campo.prezzoOrario}", color = Color.White, fontFamily = futuraBookFontFamily)
            CustomText(text = "N. giocatori: ${campo.numeroGiocatori}", color = Color.White, fontFamily = futuraBookFontFamily)
            CustomText(text = "Spogliatoi: ${if (campo.spogliatoi) "Sì" else "No"}", color = Color.White, fontFamily = futuraBookFontFamily)

            Spacer(Modifier.height(15.dp))
            Text(text = "📆 Orari disponibili:", fontSize = 16.sp, color = Color(0xFFCFFF5E), fontFamily = futuraBookFontFamily)

            Spacer(Modifier.height(4.dp))
            campo.disponibilitaSettimanale.forEach { giorno ->
                GiornoDisponibilitaRow(giorno)
            }

            Spacer(Modifier.height(12.dp))

            dataSelezionata?.let {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                Spacer(Modifier.height(8.dp))
                CustomText(text = "📅 Data selezionata: ${sdf.format(it)}", color = Color.White, fontFamily = futuraBookFontFamily)
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
                CustomText(
                    text = "⏱️ Orari selezionati:",
                    color = Color.White,
                    fontFamily = futuraBookFontFamily
                )
                val raggruppati = raggruppaSlotConsecutivi(orariSelezionati)
                raggruppati.forEach { (inizio, fine) ->
                    CustomText(text = "- $inizio → $fine", color = Color.White)
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

    CustomText(text = "$nomeGiorno: ${giorno.orarioApertura} - ${giorno.orarioChiusura}", color = Color.White)
}
