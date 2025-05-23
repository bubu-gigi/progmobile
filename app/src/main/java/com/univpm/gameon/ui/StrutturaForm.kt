package com.univpm.gameon.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.enums.Sport
import com.univpm.gameon.data.collections.enums.TipologiaTerreno
import com.google.android.gms.maps.model.LatLng

@Composable
fun StrutturaFormScreen(
    onSubmit: (Struttura) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var indirizzo by remember { mutableStateOf("") }
    var citta by remember { mutableStateOf("") }
    var latLng by remember { mutableStateOf<LatLng?>(null) }
    var sportSelezionati by remember { mutableStateOf(listOf<Sport>()) }
    var campi by remember { mutableStateOf(listOf<Campo>()) }

    var showCampoDialog by remember { mutableStateOf(false) }

    Column (modifier = modifier.padding(16.dp)) {
        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome struttura") })
        Spacer(Modifier.height(8.dp))

        GooglePlacesAutocomplete(
            onPlaceSelected = { place, location ->
                indirizzo = place
                latLng = location
            }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(value = citta, onValueChange = { citta = it }, label = { Text("Città") })

        Spacer(Modifier.height(8.dp))
        Text("Sport praticabili:")
        Sport.entries.forEach { sport ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = sportSelezionati.contains(sport),
                    onCheckedChange = {
                        sportSelezionati = if (it) sportSelezionati + sport else sportSelezionati - sport
                    }
                )
                Text(sport.name)
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { showCampoDialog = true }) {
            Text("Aggiungi campo")
        }

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(campi) { campo ->
                Text("🟢 ${campo.nomeCampo} (${campo.sport.name})")
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                latLng?.let {
                    val struttura = Struttura(
                        nome = nome,
                        indirizzo = indirizzo,
                        citta = citta,
                        latitudine = it.latitude,
                        longitudine = it.longitude,
                        sportPraticabili = sportSelezionati,
                        campi = campi
                    )
                    onSubmit(struttura)
                }
            },
            enabled = latLng != null && nome.isNotBlank() && citta.isNotBlank()
        ) {
            Text("Salva struttura")
        }
    }

    if (showCampoDialog) {
        Dialog(onDismissRequest = { showCampoDialog = false }) {
            CampoFormDialog(onCampoAdded = {
                campi = campi + it
                showCampoDialog = false
            })
        }
    }
}

@Composable
fun GooglePlacesAutocomplete(onPlaceSelected: (String, LatLng) -> Unit) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val address = data.getStringExtra("address") ?: ""
                val lat = data.getDoubleExtra("lat", 0.0)
                val lng = data.getDoubleExtra("lng", 0.0)
                onPlaceSelected(address, LatLng(lat, lng))
            }
        }
    }

    Button(onClick = {
        val intent = Intent(context, PlacePickerActivity::class.java)
        launcher.launch(intent)
    }) {
        Text("Cerca indirizzo")
    }
}


@Composable
fun CampoFormDialog(onCampoAdded: (Campo) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var sport by remember { mutableStateOf(Sport.CALCIO5) }
    var terreno by remember { mutableStateOf(TipologiaTerreno.ERBA_SINTETICA) }
    var prezzo by remember { mutableStateOf("20.0") }
    var numGiocatori by remember { mutableStateOf("5") }
    var spogliatoi by remember { mutableStateOf(false) }

    Surface (
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome campo") })
            DropdownMenuEnum("Sport", Sport.entries, sport) { sport = it }
            DropdownMenuEnum("Terreno", TipologiaTerreno.entries, terreno) { terreno = it }

            OutlinedTextField(value = prezzo, onValueChange = { prezzo = it }, label = { Text("Prezzo/h") })
            OutlinedTextField(value = numGiocatori, onValueChange = { numGiocatori = it }, label = { Text("Num. giocatori") })

            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = spogliatoi, onCheckedChange = { spogliatoi = it })
                Text("Spogliatoi disponibili")
            }

            Spacer(Modifier.height(8.dp))

            Button(onClick = {
                onCampoAdded(
                    Campo(
                        nomeCampo = nome,
                        sport = sport,
                        tipologiaTerreno = terreno,
                        prezzoOrario = prezzo.toDoubleOrNull() ?: 0.0,
                        numeroGiocatori = numGiocatori.toIntOrNull() ?: 0,
                        spogliatoi = spogliatoi,
                        strutturaId = "" // lo inserirai dopo
                    )
                )
            }) {
                Text("Aggiungi campo")
            }
        }
    }
}

@Composable
fun <T : Enum<T>> DropdownMenuEnum(label: String, values: List<T>, selected: T, onSelect: (T) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box{
        OutlinedButton(onClick = { expanded = true }) {
            Text("$label: ${selected.name}")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            values.forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStrutturaFormScreen() {
    StrutturaFormScreen(onSubmit = {
        // Preview: non fa nulla
    })
}

@Preview(showBackground = true)
@Composable
fun PreviewCampoFormDialog() {
    Dialog(onDismissRequest = {}) {
        CampoFormDialog(onCampoAdded = {
            // Simulazione: stampa o log, ma non fa nulla in preview
        })
    }
}

