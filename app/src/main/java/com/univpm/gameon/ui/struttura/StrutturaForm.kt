package com.univpm.gameon.ui.struttura

import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.GooglePlacesAutocomplete
import com.univpm.gameon.ui.components.OutlinedInputField
import com.univpm.gameon.viewmodels.StruttureViewModel

@Composable
fun StrutturaFormScreen(
    navController: NavController,
    strutturaDaModificare: Struttura? = null,
    campiEsistenti: List<Campo> = emptyList()
) {
    val struttureViewModel: StruttureViewModel = hiltViewModel()
    val context = LocalContext.current
    val isEdit = strutturaDaModificare != null

    var nome by remember { mutableStateOf(strutturaDaModificare?.nome ?: "") }
    var indirizzo by remember { mutableStateOf(strutturaDaModificare?.indirizzo ?: "") }
    var citta by remember { mutableStateOf(strutturaDaModificare?.citta ?: "") }
    var latLng by remember { mutableStateOf(strutturaDaModificare?.let { LatLng(it.latitudine, it.longitudine) }) }
    var campi by remember { mutableStateOf(campiEsistenti.toMutableList()) }
    var campoInModifica by remember { mutableStateOf<Campo?>(null) }
    var showCampoDialog by remember { mutableStateOf(false) }

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
                label = "Indirizzo completo",
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
                CustomText(
                    text = "Campi aggiunti:",
                    fontSize = 16.sp
                )

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
                                    onClick = { campi =
                                        campi.filterNot { it == campo } as MutableList<Campo>
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
                        val struttura = (strutturaDaModificare?.copy(
                            nome = nome,
                            indirizzo = indirizzo,
                            citta = citta,
                            latitudine = it.latitude,
                            longitudine = it.longitude,
                            sportPraticabili = campi.map { it.sport }.distinct()
                        ) ?: Struttura(
                            nome = nome,
                            indirizzo = indirizzo,
                            citta = citta,
                            latitudine = it.latitude,
                            longitudine = it.longitude,
                            sportPraticabili = campi.map { it.sport }.distinct()
                        ))

                        if (isEdit) {
                            struttureViewModel.aggiornaStruttura(strutturaDaModificare!!.id, struttura, campi)
                        } else {
                            struttureViewModel.salvaStruttura(struttura, campi)
                        }

                        navController.navigate(StruttureListRoute)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            )

            if (isEdit) {
                ButtonComponent(
                    text = "Elimina struttura",
                    onClick = {
                        struttureViewModel.eliminaStruttura(strutturaDaModificare!!.id)
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
                        campi = (if (campoInModifica != null) {
                            campi.map { if (it == campoInModifica) nuovoCampo.copy(id = campoInModifica!!.id) else it }
                        } else {
                            campi + nuovoCampo
                        }).toMutableList()
                        showCampoDialog = false
                        campoInModifica = null
                    }
                )
            }
        }
    }
}
