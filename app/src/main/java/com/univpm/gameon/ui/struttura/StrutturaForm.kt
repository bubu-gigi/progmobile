package com.univpm.gameon.ui.struttura

import android.location.Geocoder
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.google.android.gms.maps.model.LatLng
import com.univpm.gameon.R
import com.univpm.gameon.core.StruttureListRoute
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.ui.components.GooglePlacesAutocomplete
import com.univpm.gameon.ui.futuraBookFontFamily
import com.univpm.gameon.ui.lemonMilkFontFamily
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