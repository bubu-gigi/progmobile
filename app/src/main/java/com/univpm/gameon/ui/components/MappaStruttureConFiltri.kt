package com.univpm.gameon.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.enums.Sport

@Composable
fun MappaStruttureConFiltri(
    strutture: List<Struttura>,
    onStrutturaSelezionata: (Struttura) -> Unit,
    userPosition: LatLng? = null,
    cameraPositionState: CameraPositionState,
    width: Dp = Dp.Unspecified,
    height: Dp = Dp.Unspecified
) {
    var sportFiltrato by remember { mutableStateOf<Sport?>(null) }
    var cittaFiltrata by remember { mutableStateOf<String?>(null) }

    val cittaPrincipali = strutture
        .map { it.citta.trim() }
        .distinct()
        .sortedBy { it.lowercase() }

    val struttureFiltrate = strutture.filter {
        (sportFiltrato == null || it.sportPraticabili.contains(sportFiltrato)) &&
                (cittaFiltrata == null || it.citta.equals(cittaFiltrata, ignoreCase = true))
    }

    LaunchedEffect(userPosition) {
        userPosition?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(it, 12f),
                durationMs = 1000
            )
        }
    }

    val containerModifier = Modifier
        .then(if (width != Dp.Unspecified) Modifier.width(width) else Modifier.fillMaxWidth())
        .then(if (height != Dp.Unspecified) Modifier.height(height) else Modifier.fillMaxHeight())

    Column(containerModifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Dropdown(
                    current = cittaFiltrata,
                    options = listOf(null) + cittaPrincipali,
                    getLabel = { it ?: "Tutte le città" },
                    label = "Filtra per città",
                    onSelected = { cittaFiltrata = it }
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                Dropdown(
                    current = sportFiltrato,
                    options = listOf(null) + Sport.entries,
                    getLabel = { it?.name ?: "Tutti gli sport" },
                    label = "Filtra per sport",
                    onSelected = { sportFiltrato = it }
                )
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
