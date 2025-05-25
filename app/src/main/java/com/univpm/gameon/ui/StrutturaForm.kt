package com.univpm.gameon.ui

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univpm.gameon.R
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.enums.Sport
import com.univpm.gameon.data.collections.enums.TipologiaTerreno
import com.google.android.gms.maps.model.LatLng
import com.univpm.gameon.core.StruttureListRoute
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
    var latLng by remember {
        mutableStateOf(
            strutturaDaModificare?.let { LatLng(it.latitudine, it.longitudine) }
        )
    }
    var campi by remember { mutableStateOf(campiEsistenti.toMutableList()) }
    var campoInModifica by remember { mutableStateOf<Campo?>(null) }
    var showCampoDialog by remember { mutableStateOf(false) }

    val borderColor = Color(0xFFE36BE0)
    val containerColor = Color(0xFF2B2B2B)

    fun getCityFromLatLng(lat: Double, lng: Double): String {
        return try {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            addresses?.firstOrNull()?.locality ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sfondocarta),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(210.dp))
            Text(
                text = if (isEdit) "Modifica Struttura:" else "Dettagli Struttura:",
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
                    citta = getCityFromLatLng(location.latitude, location.longitude)
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = indirizzo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Indirizzo completo", fontFamily = futuraBookFontFamily, color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                )
            )

            Spacer(Modifier.height(2.dp))

            Button(
                onClick = {
                    campoInModifica = null
                    showCampoDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                    .background(color = Color(0xFF6136FF), shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
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

            Spacer(Modifier.height(4.dp))

            // Bottone salva/aggiorna
            Button(
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
                enabled = latLng != null && nome.isNotBlank() && indirizzo.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                    .background(color = Color(0xFF6136FF), shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = if (isEdit) "Aggiorna struttura" else "Salva struttura",
                    color = Color(0xFFCFFF5E),
                    fontSize = 18.sp,
                    fontFamily = futuraBookFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
            if (isEdit) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        struttureViewModel.eliminaStruttura(strutturaDaModificare!!.id, "")
                        navController.navigate(StruttureListRoute)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, Color.Red), shape = RoundedCornerShape(12.dp))
                        .background(color = Color.Red, shape = RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = "Elimina struttura",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = futuraBookFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

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
                    }) as MutableList<Campo>
                    showCampoDialog = false
                    campoInModifica = null
                }
            )
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoFormDialog(
    campoDaModificare: Campo? = null,
    onCampoAdded: (Campo) -> Unit
) {
    var nome by remember { mutableStateOf(campoDaModificare?.nomeCampo ?: "") }
    var sport by remember { mutableStateOf(campoDaModificare?.sport ?: Sport.CALCIO5) }
    var terreno by remember { mutableStateOf(campoDaModificare?.tipologiaTerreno ?: TipologiaTerreno.ERBA_SINTETICA) }
    var prezzo by remember { mutableStateOf(campoDaModificare?.prezzoOrario?.toString() ?: "20.0") }
    var numGiocatori by remember { mutableStateOf(campoDaModificare?.numeroGiocatori?.toString() ?: "5") }
    var spogliatoi by remember { mutableStateOf(campoDaModificare?.spogliatoi ?: false) }

    val borderColor = Color(0xFFE36BE0)
    val containerColor = Color(0xFF2B2B2B)
    var menuSportEspanso by remember { mutableStateOf(false) }
    var menuTerrenoEspanso by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { }) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.sfondogrigio),
                contentDescription = "Sfondo dialog",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (campoDaModificare != null) "Modifica campo:" else "Aggiungi un nuovo campo:",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontFamily = lemonMilkFontFamily
                        )
                    )

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome campo", fontFamily = futuraBookFontFamily, color = Color.White) },
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

                    Text("Sport", color = Color.White, fontFamily = futuraBookFontFamily, fontWeight = FontWeight.Bold)
                    ExposedDropdownMenuBox(
                        expanded = menuSportEspanso,
                        onExpandedChange = { menuSportEspanso = !menuSportEspanso }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = sport.name,
                            onValueChange = {},
                            label = { Text("Sport", fontFamily = futuraBookFontFamily, color = Color.White) },
                            trailingIcon = {
                                androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuSportEspanso)
                            },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = containerColor,
                                unfocusedContainerColor = containerColor,
                                focusedBorderColor = borderColor,
                                unfocusedBorderColor = borderColor,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color.White
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = menuSportEspanso,
                            onDismissRequest = { menuSportEspanso = false }
                        ) {
                            Sport.entries.forEach {
                                DropdownMenuItem(
                                    text = { Text(it.name, color = Color.White) },
                                    onClick = {
                                        sport = it
                                        menuSportEspanso = false
                                    }
                                )
                            }
                        }
                    }

                    Text("Terreno", color = Color.White, fontFamily = futuraBookFontFamily, fontWeight = FontWeight.Bold)
                    ExposedDropdownMenuBox(
                        expanded = menuTerrenoEspanso,
                        onExpandedChange = { menuTerrenoEspanso = !menuTerrenoEspanso }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = terreno.name,
                            onValueChange = {},
                            label = { Text("Terreno", fontFamily = futuraBookFontFamily, color = Color.White) },
                            trailingIcon = {
                                androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuTerrenoEspanso)
                            },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = containerColor,
                                unfocusedContainerColor = containerColor,
                                focusedBorderColor = borderColor,
                                unfocusedBorderColor = borderColor,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color.White
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = menuTerrenoEspanso,
                            onDismissRequest = { menuTerrenoEspanso = false }
                        ) {
                            TipologiaTerreno.entries.forEach {
                                DropdownMenuItem(
                                    text = { Text(it.name, color = Color.White) },
                                    onClick = {
                                        terreno = it
                                        menuTerrenoEspanso = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = prezzo,
                        onValueChange = { prezzo = it },
                        label = { Text("Prezzo/h", fontFamily = futuraBookFontFamily, color = Color.White) },
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
                    OutlinedTextField(
                        value = numGiocatori,
                        onValueChange = { numGiocatori = it },
                        label = { Text("Num. giocatori", fontFamily = futuraBookFontFamily, color = Color.White) },
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = spogliatoi,
                            onCheckedChange = { spogliatoi = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFCFFF5E),
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.Black
                            )
                        )
                        Text(
                            text = "Spogliatoi disponibili",
                            color = Color.White,
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
                                    spogliatoi = spogliatoi
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
                            text = if (campoDaModificare != null) "Modifica campo" else "Aggiungi campo",
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


@Preview(showBackground = true)
@Composable
fun PreviewStrutturaFormScreen() {
    val fakeNav: NavController = rememberNavController()
    StrutturaFormScreen(fakeNav)
}

@Preview(showBackground = true)
@Composable
fun PreviewCampoFormDialog() {
    CampoFormDialog(onCampoAdded = {
        // Simulazione: stampa o log, ma non fa nulla in preview
    })
}