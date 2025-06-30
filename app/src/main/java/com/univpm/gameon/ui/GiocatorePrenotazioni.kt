
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.univpm.gameon.core.StrutturaDettaglioRoute
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.core.futuraBookFontFamily
import com.univpm.gameon.core.lemonMilkFontFamily
import com.univpm.gameon.ui.components.MappaStruttureConFiltri
import com.univpm.gameon.viewmodels.PrenotazioneViewModel
import com.univpm.gameon.viewmodels.StruttureViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GiocatorePrenotazioniScreen(navController: NavController) {
    val struttureViewModel: StruttureViewModel = hiltViewModel()
    val prenotazioniViewModel: PrenotazioneViewModel = hiltViewModel()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val strutture by struttureViewModel.strutture.collectAsState()
    val prenotazioni by prenotazioniViewModel.prenotazioniUtente.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val opzioniFiltro = listOf("Tutte", "Attive", "Passate")
    var filtroSelezionato by remember { mutableStateOf("Tutte") }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        struttureViewModel.caricaStrutture()
        prenotazioniViewModel.caricaPrenotazioniUtente(UserSessionManager.userId.orEmpty())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Le tue prenotazioni", color = Color.White, fontSize = 20.sp, fontFamily = lemonMilkFontFamily)
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = filtroSelezionato,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filtra per") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opzioniFiltro.forEach { opzione ->
                        DropdownMenuItem(
                            text = { Text(opzione) },
                            onClick = {
                                filtroSelezionato = opzione
                                expanded = false
                            }
                        )
                    }
                }
            }


            val prenotazioniFiltrate = prenotazioni.filter { pren ->
                val oggi = LocalDate.now()
                val ora = LocalTime.now()

                when (filtroSelezionato) {
                    "Attive" -> {
                        val dataPren = LocalDate.parse(pren.data, formatter)
                        val orarioFine = LocalTime.parse(pren.orarioFine)
                        (dataPren.isAfter(oggi) || (dataPren.isEqual(oggi) && orarioFine.isAfter(ora)))
                    }

                    "Passate" -> {
                        val dataPren = LocalDate.parse(pren.data, formatter)
                        val orarioFine = LocalTime.parse(pren.orarioFine)
                        (dataPren.isBefore(oggi) || (dataPren.isEqual(oggi) && orarioFine.isBefore(ora)))
                    }

                    else -> true
                }
            }

            if (prenotazioni.isEmpty()) {
                Text("Nessuna prenotazione trovata", color = Color.White)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(prenotazioniFiltrate) { prenotazione ->
                        val struttura = strutture.find { it.id == prenotazione.strutturaId }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(BorderStroke(2.dp, Color(0xFFE36BE0))),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = struttura?.nome ?: "Struttura sconosciuta",
                                    color = Color.White,
                                    fontFamily = lemonMilkFontFamily,
                                    fontSize = 18.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Campo: ${prenotazione.campoId}",
                                    color = Color.White,
                                    fontFamily = futuraBookFontFamily
                                )
                                Text(
                                    text = "Data: ${prenotazione.data}",
                                    color = Color.White,
                                    fontFamily = futuraBookFontFamily
                                )
                                Text(
                                    text = "Orario: ${prenotazione.orarioInizio} - ${prenotazione.orarioFine}",
                                    color = Color.White,
                                    fontFamily = futuraBookFontFamily
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "Dettagli",
                                        color = Color(0xFF69C9FF),
                                        modifier = Modifier
                                            .clickable {
                                                navController.navigate(StrutturaDettaglioRoute(struttura?.id ?: ""))
                                            }
                                    )
                                    Text(
                                        text = "Elimina",
                                        color = Color.Red,
                                        modifier = Modifier.clickable {
                                            showDialog = true
                                        }
                                    )
                                }
                            }
                        }

                        if (showDialog) {
                            androidx.compose.material3.AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Conferma eliminazione") },
                                text = { Text("Sei sicuro di voler annullare questa prenotazione?") },
                                confirmButton = {
                                    TextButton(onClick = {
                                        prenotazioniViewModel.annullaPrenotazione(prenotazione.id) {
                                            prenotazioniViewModel.caricaPrenotazioniUtente(UserSessionManager.userId.orEmpty())
                                        }
                                        showDialog = false
                                    }) {
                                        Text("Conferma")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        showDialog = false
                                    }) {
                                        Text("Annulla")
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            MappaStruttureConFiltri(
                strutture = strutture,
                onStrutturaSelezionata = {struttura -> navController.navigate(StrutturaDettaglioRoute(struttura.id))},
                height = 300.dp
            )

        }
    }
}
