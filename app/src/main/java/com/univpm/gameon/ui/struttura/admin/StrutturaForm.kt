package com.univpm.gameon.ui.struttura.admin

import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.univpm.gameon.core.StruttureListRoute
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.ui.components.*
import com.univpm.gameon.viewmodels.StruttureViewModel

@Composable
fun StrutturaFormScreen(
    navController: NavController,
    strutturaId: String? = null
) {
    val struttureViewModel: StruttureViewModel = hiltViewModel()
    val context = LocalContext.current
    val isEdit = strutturaId != null

    val struttura by struttureViewModel.strutturaSelezionata.collectAsState()
    val campiStruttura by struttureViewModel.campiStruttura.collectAsState()

    var nome by remember { mutableStateOf("") }
    var indirizzo by remember { mutableStateOf("") }
    var citta by remember { mutableStateOf("") }
    var latLng by remember { mutableStateOf<LatLng?>(null) }
    var campi by remember { mutableStateOf(mutableListOf<Campo>()) }
    var campoInModifica by remember { mutableStateOf<Campo?>(null) }
    var showCampoDialog by remember { mutableStateOf(false) }
    var inizializzato by remember { mutableStateOf(false) }

    LaunchedEffect(strutturaId) {
        if (isEdit) {
            struttureViewModel.caricaStruttura(strutturaId!!)
        }
    }

    LaunchedEffect(struttura, campiStruttura) {
        if (isEdit && struttura != null && !inizializzato) {
            nome = struttura!!.nome
            indirizzo = struttura!!.indirizzo
            citta = struttura!!.citta
            latLng = LatLng(struttura!!.latitudine, struttura!!.longitudine)
            campi = campiStruttura.toMutableList()
            inizializzato = true
        }
    }

    fun getCityFromLatLng(lat: Double, lng: Double): String {
        return try {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            addresses?.firstOrNull()?.locality ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    BackgroundScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(210.dp))

            CustomText(
                text = if (isEdit) "Modifica Struttura:" else "Dettagli Struttura:",
                fontSize = 23.sp
            )

            OutlinedInputField(
                value = nome,
                onValueChange = { nome = it },
                label = "Nome struttura"
            )

            GooglePlacesAutocomplete(
                onPlaceSelected = { place, location ->
                    indirizzo = place
                    latLng = location
                    citta = getCityFromLatLng(location.latitude, location.longitude)
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedInputField(
                value = indirizzo,
                onValueChange = {},
                label = "Indirizzo completo"
            )

            ButtonComponent(
                text = "Aggiungi campo",
                onClick = {
                    campoInModifica = null
                    showCampoDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (campi.isNotEmpty()) {
                CustomText(text = "Campi aggiunti:", fontSize = 16.sp)

                LazyColumn(modifier = Modifier.height(100.dp)) {
                    items(campi) { campo ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🟢 ${campo.nomeCampo} (${campo.sport.name})", color = Color.White)
                            Row {
                                Button(
                                    onClick = {
                                        campoInModifica = campo
                                        showCampoDialog = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                                ) {
                                    Text("Modifica", color = Color.White)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        campi = campi.filterNot { it == campo }.toMutableList()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) {
                                    Text("Elimina", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            ButtonComponent(
                text = if (isEdit) "Aggiorna struttura" else "Salva struttura",
                onClick = {
                    latLng?.let {
                        val nuovaStruttura = struttura?.copy(
                            nome = nome,
                            indirizzo = indirizzo,
                            citta = citta,
                            latitudine = it.latitude,
                            longitudine = it.longitude,
                            sportPraticabili = campi.map { campo -> campo.sport }.distinct()
                        ) ?: Struttura(
                            nome = nome,
                            indirizzo = indirizzo,
                            citta = citta,
                            latitudine = it.latitude,
                            longitudine = it.longitude,
                            sportPraticabili = campi.map { campo -> campo.sport }.distinct()
                        )

                        if (isEdit) {
                            struttureViewModel.aggiornaStruttura(strutturaId!!, nuovaStruttura, campi)
                        } else {
                            struttureViewModel.salvaStruttura(nuovaStruttura, campi)
                        }

                        navController.navigate(StruttureListRoute)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            if (isEdit && struttura != null) {
                ButtonComponent(
                    text = "Elimina struttura",
                    onClick = {
                        struttureViewModel.eliminaStruttura(strutturaId!!)
                        navController.navigate(StruttureListRoute)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }

        if (showCampoDialog) {
            Dialog(onDismissRequest = { showCampoDialog = false }) {
                CampoFormDialog(
                    campoDaModificare = campoInModifica,
                    onCampoAdded = { nuovoCampo ->
                        campi = if (campoInModifica != null) {
                            campi.map {
                                if (it == campoInModifica) nuovoCampo.copy(id = campoInModifica!!.id)
                                else it
                            }.toMutableList()
                        } else {
                            (campi + nuovoCampo).toMutableList()
                        }
                        campoInModifica = null
                        showCampoDialog = false
                    }
                )
            }
        }
    }
}
