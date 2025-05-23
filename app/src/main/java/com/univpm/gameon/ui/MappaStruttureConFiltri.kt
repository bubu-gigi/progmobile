package com.univpm.gameon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.enums.Sport

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MappaStruttureConFiltri(
    strutture: List<Struttura>,
    onStrutturaSelezionata: (Struttura) -> Unit
) {
    var sportFiltrato by remember { mutableStateOf<Sport?>(null) }
    var menuSportEspanso by remember { mutableStateOf(false) }

    var cittaFiltrata by remember { mutableStateOf<String?>(null) }
    var menuCittaEspanso by remember { mutableStateOf(false) }

    val cittaPrincipali = listOf(
        "Roma", "Milano", "Napoli", "Torino", "Palermo",
        "Genova", "Bologna", "Firenze", "Bari", "Catania",
        "Venezia", "Verona", "Messina", "Padova", "Trieste"
    )

    val struttureFiltrate = strutture.filter {
        (sportFiltrato == null || it.sportPraticabili.contains(sportFiltrato)) &&
                (cittaFiltrata == null || it.citta.equals(cittaFiltrata, ignoreCase = true))
    }

    val cameraPositionState = rememberCameraPositionState {
        if (strutture.isNotEmpty()) {
            position = CameraPosition.fromLatLngZoom(
                LatLng(strutture.first().latitudine, strutture.first().longitudine),
                10f
            )
        }
    }

    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = menuCittaEspanso,
                    onExpandedChange = { menuCittaEspanso = !menuCittaEspanso }
                ) {
                    TextField(
                        readOnly = true,
                        value = cittaFiltrata ?: "Tutte le città",
                        onValueChange = {},
                        label = { Text("Filtra per città") },
                        trailingIcon = { TrailingIcon(expanded = menuCittaEspanso) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = menuCittaEspanso,
                        onDismissRequest = { menuCittaEspanso = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tutte le città") },
                            onClick = {
                                cittaFiltrata = null
                                menuCittaEspanso = false
                            }
                        )
                        cittaPrincipali.forEach { citta ->
                            DropdownMenuItem(
                                text = { Text(citta) },
                                onClick = {
                                    cittaFiltrata = citta
                                    menuCittaEspanso = false
                                }
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = menuSportEspanso,
                    onExpandedChange = { menuSportEspanso = !menuSportEspanso }
                ) {
                    TextField(
                        readOnly = true,
                        value = sportFiltrato?.name ?: "Tutti gli sport",
                        onValueChange = {},
                        label = { Text("Filtra per sport") },
                        trailingIcon = { TrailingIcon(expanded = menuSportEspanso) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = menuSportEspanso,
                        onDismissRequest = { menuSportEspanso = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tutti gli sport") },
                            onClick = {
                                sportFiltrato = null
                                menuSportEspanso = false
                            }
                        )
                        Sport.entries.forEach { sport ->
                            DropdownMenuItem(
                                text = { Text(sport.name) },
                                onClick = {
                                    sportFiltrato = sport
                                    menuSportEspanso = false
                                }
                            )
                        }
                    }
                }
            }
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            struttureFiltrate.forEach { struttura ->
                Marker(
                    state = MarkerState(position = LatLng(struttura.latitudine, struttura.longitudine)),
                    title = struttura.nome,
                    snippet = struttura.indirizzo,
                    onClick = {
                        onStrutturaSelezionata(struttura)
                        false
                    }
                )
            }
        }
    }
}