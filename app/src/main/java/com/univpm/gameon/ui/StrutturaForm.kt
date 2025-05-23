package com.univpm.gameon.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.univpm.gameon.R
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

    val borderColor = Color(0xFFE36BE0)
    val containerColor = Color(0xFF2B2B2B)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.sfondocarta),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(210.dp))
            Text(
                text = "Dettagli Struttura:",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontSize = 23.sp,
                    fontFamily = lemonMilkFontFamily
                )
            )

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome struttura", fontFamily = futuraBookFontFamily, color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                )
            )

            GooglePlacesAutocomplete(
                onPlaceSelected = { place, location ->
                    indirizzo = place
                    latLng = location
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = citta,
                onValueChange = { citta = it },
                label = { Text("Città", fontFamily = futuraBookFontFamily, color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                )
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Sport praticabili:",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontFamily = futuraBookFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(4.dp))

                val sportPairs = listOf(
                    Pair(Sport.CALCIO5, Sport.CALCIO8),
                    Pair(Sport.TENNIS, Sport.TENNISDOPPIO),
                    Pair(Sport.PADEL, Sport.PADELDOPPIO),
                    Pair(Sport.BEACHVOLLEY, null)
                )

                sportPairs.forEach { (sport1, sport2) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = sportSelezionati.contains(sport1),
                                onCheckedChange = {
                                    sportSelezionati = if (it) sportSelezionati + sport1 else sportSelezionati - sport1
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFFCFFF5E),
                                    uncheckedColor = Color.White,
                                    checkmarkColor = Color.Black
                                )
                            )
                            Text(
                                sport1.name.replace("_", " "),
                                color = Color.White,
                                fontFamily = futuraBookFontFamily,
                                fontWeight = FontWeight.Normal
                            )
                        }

                        sport2?.let {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = sportSelezionati.contains(it),
                                    onCheckedChange = { isChecked ->
                                        sportSelezionati = if (isChecked) sportSelezionati + it else sportSelezionati - it
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFFCFFF5E),
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color.Black
                                    )
                                )
                                Text(
                                    it.name.replace("_", " "),
                                    color = Color.White,
                                    fontFamily = futuraBookFontFamily,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(2.dp))

            Button(
                onClick = { showCampoDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                    .background(
                        color = Color(0xFF6136FF),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Aggiungi campo",
                    color = Color(0xFFCFFF5E),
                    fontSize = 18.sp,
                    fontFamily = futuraBookFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }

            if (campi.isNotEmpty()) {
                Text(
                    text = "Campi aggiunti:",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontFamily = futuraBookFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                LazyColumn(modifier = Modifier.height(100.dp)) {
                    items(campi) { campo ->
                        Text("🟢 ${campo.nomeCampo} (${campo.sport.name})", color = Color.White)
                    }
                }
            }
            Spacer(Modifier.height(4.dp))

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
                enabled = latLng != null && nome.isNotBlank() && citta.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                    .background(
                        color = Color(0xFF6136FF),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Salva struttura",
                    color = Color(0xFFCFFF5E),
                    fontSize = 18.sp,
                    fontFamily = futuraBookFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
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
fun GooglePlacesAutocomplete(onPlaceSelected: (String, LatLng) -> Unit, modifier: Modifier = Modifier) {
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

    OutlinedButton(
        onClick = {
            val intent = Intent(context, PlacePickerActivity::class.java)
            launcher.launch(intent)
        },
        modifier = modifier
            .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFFCFFF5E),
            containerColor = Color(0xFF6136FF)
        )
    ) {
        Text(
            text = "Cerca indirizzo",
            fontSize = 18.sp,
            fontFamily = futuraBookFontFamily,
            fontWeight = FontWeight.Bold
        )
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

    val borderColor = Color(0xFFE36BE0)
    val containerColor = Color(0xFF2B2B2B) // Colore per lo sfondo scuro dei TextField in CampoFormDialog

    Dialog(onDismissRequest = { /* Handle dismiss */ }) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.sfondocarta), // Immagine di sfondo
                contentDescription = "Sfondo dialog",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.Transparent // Rende la Surface trasparente per mostrare l'immagine sottostante
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Aggiungi un nuovo campo:",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White, // Testo bianco per il titolo
                            fontFamily = lemonMilkFontFamily
                        )
                    )
                    Spacer(Modifier.height(4.dp))

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome campo", fontFamily = futuraBookFontFamily, color = Color.White) }, // Etichetta bianca
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White), // Testo bianco
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = borderColor,
                            unfocusedBorderColor = borderColor,
                            cursorColor = Color.White, // Cursore bianco
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = containerColor, // Sfondo scuro
                            unfocusedContainerColor = containerColor, // Sfondo scuro
                        )
                    )

                    DropdownMenuEnum<Sport>("Sport", Sport.entries, sport) { sport = it }
                    DropdownMenuEnum<TipologiaTerreno>("Terreno", TipologiaTerreno.entries, terreno) { terreno = it }

                    OutlinedTextField(
                        value = prezzo,
                        onValueChange = { prezzo = it },
                        label = { Text("Prezzo/h", fontFamily = futuraBookFontFamily, color = Color.White) }, // Etichetta bianca
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White), // Testo bianco
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = borderColor,
                            unfocusedBorderColor = borderColor,
                            cursorColor = Color.White, // Cursore bianco
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = containerColor, // Sfondo scuro
                            unfocusedContainerColor = containerColor, // Sfondo scuro
                        )
                    )
                    OutlinedTextField(
                        value = numGiocatori,
                        onValueChange = { numGiocatori = it },
                        label = { Text("Num. giocatori", fontFamily = futuraBookFontFamily, color = Color.White) }, // Etichetta bianca
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White), // Testo bianco
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = borderColor,
                            unfocusedBorderColor = borderColor,
                            cursorColor = Color.White, // Cursore bianco
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = containerColor, // Sfondo scuro
                            unfocusedContainerColor = containerColor, // Sfondo scuro
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = spogliatoi,
                            onCheckedChange = { spogliatoi = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFCFFF5E),
                                uncheckedColor = Color.White, // Checkbox non selezionata bianca
                                checkmarkColor = Color.Black
                            )
                        )
                        Text(
                            text = "Spogliatoi disponibili",
                            color = Color.White, // Testo bianco
                            fontFamily = futuraBookFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            onCampoAdded(
                                Campo(
                                    nomeCampo = nome,
                                    sport = sport,
                                    tipologiaTerreno = terreno,
                                    prezzoOrario = prezzo.toDoubleOrNull() ?: 0.0,
                                    numeroGiocatori = numGiocatori.toIntOrNull() ?: 0,
                                    spogliatoi = spogliatoi,
                                    strutturaId = ""
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                            .background(
                                color = Color(0xFF6136FF),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Aggiungi campo",
                            color = Color(0xFFCFFF5E),
                            fontSize = 18.sp,
                            fontFamily = futuraBookFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun <T : Enum<T>> DropdownMenuEnum(label: String, values: List<T>, selected: T, onSelect: (T) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val borderColor = Color(0xFFE36BE0)
    val containerColor = Color(0xFF2B2B2B) // Colore per lo sfondo scuro del DropdownMenuEnum

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selected.name,
            onValueChange = {},
            label = { Text(label, fontFamily = futuraBookFontFamily, color = Color.White) }, // Etichetta bianca
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White), // Testo bianco
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.White, // Cursore bianco
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = containerColor, // Sfondo scuro
                unfocusedContainerColor = containerColor, // Sfondo scuro
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color(0xFF2B2B2B)) // Sfondo scuro per il menu dropdown
        ) {
            values.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.name,
                            color = Color.White, // Testo bianco per le voci del menu
                            fontFamily = futuraBookFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    },
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
    CampoFormDialog(onCampoAdded = {
        // Simulazione: stampa o log, ma non fa nulla in preview
    })
}