package com.univpm.gameon.ui.prenotazioni

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.enums.Sport
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.Dropdown
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.univpm.gameon.viewmodels.PrenotazioneViewModel
import com.univpm.gameon.viewmodels.StruttureViewModel
import kotlinx.coroutines.launch

@Composable
fun GestionePrenotazioniAdminScreen() {
    val struttureViewModel: StruttureViewModel = hiltViewModel()
    val prenotazioneViewModel: PrenotazioneViewModel = hiltViewModel()

    val strutture by struttureViewModel.strutture.collectAsState()
    val prenotazioni by prenotazioneViewModel.prenotazioni.collectAsState()
    val errore by struttureViewModel.errore.collectAsState()

    var inizializzato by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!inizializzato) {
            struttureViewModel.caricaStrutture()
            prenotazioneViewModel.caricaTuttePrenotazioni()
            inizializzato = true
        }
    }

    when {
        errore != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Errore: $errore")
            }
        }

        strutture.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Caricamento strutture...")
            }
        }

        else -> {
            GestionePrenotazioniAdminScreenContent(
                strutture = strutture,
                prenotazioni = prenotazioni,
                prenotazioneViewModel = prenotazioneViewModel
            )
        }
    }
}


@Composable
fun GestionePrenotazioniAdminScreenContent(
    strutture: List<Struttura>,
    prenotazioni: List<Prenotazione>,
    prenotazioneViewModel: PrenotazioneViewModel
) {
    var filtroCitta by remember { mutableStateOf<String?>(null) }
    var filtroSport by remember { mutableStateOf<Sport?>(null) }
    var filtroStrutturaId by remember { mutableStateOf<String?>(null) }
    var prenotazioneSelezionata by remember { mutableStateOf<Prenotazione?>(null) }

    val scope = rememberCoroutineScope()

    val tutteLeCitta = listOf<String?>(null) + strutture.map { it.citta }.distinct()
    val tuttiGliSport = listOf<Sport?>(null) + strutture.flatMap { it.sportPraticabili }.distinct()
    val tutteLeStrutture = listOf<String?>(null) + strutture.map { it.id }
    val tutteLePrenotazioniFiltrate = remember(filtroCitta, filtroSport, filtroStrutturaId) {
        prenotazioni.filter { p ->
            val struttura = strutture.firstOrNull { it.id == p.strutturaId } ?: return@filter false
            val matchCitta = filtroCitta == null || struttura.citta == filtroCitta
            val matchSport = filtroSport == null || struttura.sportPraticabili.contains(filtroSport)
            val matchStruttura = filtroStrutturaId == null || struttura.id == filtroStrutturaId
            matchCitta && matchSport && matchStruttura
        }
    }

    BackgroundScaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(210.dp))
            CustomText("Filtra prenotazioni", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))



            Row(modifier = Modifier.fillMaxWidth()) {
                Dropdown(
                    current = filtroCitta,
                    options = tutteLeCitta,
                    getLabel = { it ?: "Tutte le città" },
                    label = "Città",
                    onSelected = { filtroCitta = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Dropdown(
                    current = filtroSport,
                    options = tuttiGliSport,
                    getLabel = { it?.label ?: "Tutti gli sport" },
                    label = "Sport",
                    onSelected = { filtroSport = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Dropdown(
                current = filtroStrutturaId,
                options = tutteLeStrutture,
                getLabel = { id -> id?.let { strutture.firstOrNull { s -> s.id == id }?.nome ?: id } ?: "Tutte le strutture" },
                label = "Struttura",
                onSelected = { filtroStrutturaId = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Divider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.3f))
                CustomText("Risultati", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, modifier = Modifier.padding(horizontal = 8.dp))
                Divider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.3f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (tutteLePrenotazioniFiltrate.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CustomText("Nessuna prenotazione trovata", color = Color.White.copy(alpha = 0.7f))
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(tutteLePrenotazioniFiltrate) { pren ->
                        val struttura = strutture.first { it.id == pren.strutturaId }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                            border = BorderStroke(2.dp, Color(0xFF6136FF))
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                                border = BorderStroke(2.dp, Color(0xFF6136FF))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clickable { prenotazioneSelezionata = pren }
                                        .padding(12.dp)
                                ) {
                                    CustomText(
                                        text = struttura.nome,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    CustomText(
                                        text = "Campo: ${pren.campoId}",
                                        fontSize = 13.sp,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                    CustomText(
                                        text = "Data: ${pren.data}",
                                        fontSize = 13.sp,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                    CustomText(
                                        text = "Orario: ${pren.orarioInizio} → ${pren.orarioFine}",
                                        fontSize = 13.sp,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            prenotazioneSelezionata?.let { pren ->
                val struttura = strutture.firstOrNull { it.id == pren.strutturaId }
                if (struttura != null) {
                    ShowDeleteDialog(
                        pren = pren,
                        struttura = struttura,
                        onDeletePrenotazione = {
                            scope.launch {
                                prenotazioneViewModel.annullaPrenotazione(it.id)
                                prenotazioneSelezionata = null
                            }
                        },
                        onDismiss = { prenotazioneSelezionata = null }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowDeleteDialog(
    pren: Prenotazione,
    struttura: Struttura,
    onDeletePrenotazione: (Prenotazione) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            CustomText("Dettagli prenotazione", fontSize = 20.sp)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CustomText("Struttura: ${struttura.nome}", color = Color.White.copy(alpha = 0.7f))
                CustomText("Campo: ${pren.campoId}", color = Color.White.copy(alpha = 0.7f))
                CustomText("Data: ${pren.data}", color = Color.White.copy(alpha = 0.7f))
                CustomText("Orario: ${pren.orarioInizio} - ${pren.orarioFine}", color = Color.White.copy(alpha = 0.7f))
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeletePrenotazione(pren) }) {
                Text("Elimina", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Chiudi", color = Color.White)
            }
        },
        containerColor = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp
    )
}
