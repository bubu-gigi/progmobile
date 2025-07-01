
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.univpm.gameon.core.StrutturaDettaglioRoute
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.Dropdown
import com.univpm.gameon.ui.components.MappaStruttureConFiltri
import com.univpm.gameon.viewmodels.PrenotazioneViewModel
import com.univpm.gameon.viewmodels.StruttureViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.univpm.gameon.core.getCurrentLocation
import com.univpm.gameon.ui.components.BackgroundScaffold2


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GiocatorePrenotazioniScreen(navController: NavController) {
    val context = LocalContext.current
    val struttureViewModel: StruttureViewModel = hiltViewModel()
    val prenotazioniViewModel: PrenotazioneViewModel = hiltViewModel()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val strutture by struttureViewModel.strutture.collectAsState()
    val prenotazioni by prenotazioniViewModel.prenotazioniUtente.collectAsState()

    var filtroSelezionato by remember { mutableStateOf("Tutte") }
    var expanded by remember { mutableStateOf(false) }
    var dialogPrenotazioneId by remember { mutableStateOf<String?>(null) }

    var userPosition by remember { mutableStateOf<LatLng?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> permissionGranted = granted }
    )

    val oggi = LocalDate.now()
    val ora = LocalTime.now()

    val prenotazioniFiltrate = prenotazioni.filter { pren ->
        val data = LocalDate.parse(pren.data, formatter)
        val fine = LocalTime.parse(pren.orarioFine)
        when (filtroSelezionato) {
            "Attive" -> data.isAfter(oggi) || (data.isEqual(oggi) && fine.isAfter(ora))
            "Passate" -> data.isBefore(oggi) || (data.isEqual(oggi) && fine.isBefore(ora))
            else -> true
        }
    }

    LaunchedEffect(Unit) {
        struttureViewModel.caricaStrutture()
        prenotazioniViewModel.caricaPrenotazioniUtente(UserSessionManager.userId.orEmpty())

        val alreadyGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (alreadyGranted) {
            permissionGranted = true
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            userPosition = getCurrentLocation(context)
        }
    }

    BackgroundScaffold2 {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                text = "Le tue prenotazioni",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Dropdown(
                current = filtroSelezionato,
                options = listOf("Tutte", "Attive", "Passate"),
                getLabel = { it },
                label = "Filtra per",
                onSelected = { filtroSelezionato = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            if (prenotazioniFiltrate.isEmpty()) {
                CustomText(text = "Nessuna prenotazione trovata", color = Color.White)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    items(prenotazioniFiltrate) { pren ->
                        val struttura = strutture.find { it.id == pren.strutturaId }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(BorderStroke(2.dp, Color(0xFFE36BE0))),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                CustomText(text = struttura?.nome ?: "Struttura sconosciuta", fontSize = 18.sp)
                                Spacer(Modifier.height(4.dp))
                                CustomText(text = "Campo: ${pren.campoNome}")
                                CustomText(text = "Data: ${pren.data}")
                                CustomText(text = "Orario: ${pren.orarioInizio} - ${pren.orarioFine}")
                                Spacer(Modifier.height(8.dp))

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CustomText(
                                        text = "Dettagli",
                                        color = Color(0xFF69C9FF),
                                        modifier = Modifier.clickable {
                                            navController.navigate(StrutturaDettaglioRoute(struttura?.id ?: ""))
                                        }
                                    )
                                    CustomText(
                                        text = "Elimina",
                                        color = Color.Red,
                                        modifier = Modifier.clickable {
                                            dialogPrenotazioneId = pren.id
                                        }
                                    )
                                }
                            }
                        }

                        if (dialogPrenotazioneId == pren.id) {
                            AlertDialog(
                                onDismissRequest = { dialogPrenotazioneId = null },
                                title = { Text("Conferma eliminazione") },
                                text = { Text("Sei sicuro di voler annullare questa prenotazione?") },
                                confirmButton = {
                                    TextButton(onClick = {
                                        prenotazioniViewModel.annullaPrenotazione(pren.id)
                                        dialogPrenotazioneId = null
                                    }) {
                                        Text("Conferma")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { dialogPrenotazioneId = null }) {
                                        Text("Annulla")
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (strutture.isNotEmpty()) {
                MappaStruttureConFiltri(
                    strutture = strutture,
                    onStrutturaSelezionata = {
                        navController.navigate(StrutturaDettaglioRoute(it.id))
                    },
                    userPosition = userPosition,
                    height = 300.dp
                )
            }
        }
    }
}
