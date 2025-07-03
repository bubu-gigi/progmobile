package com.univpm.gameon.ui.struttura.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.univpm.gameon.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.univpm.gameon.core.futuraBookFontFamily
import com.univpm.gameon.core.giornoLabel
import com.univpm.gameon.core.lemonMilkFontFamily
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.enums.Sport
import com.univpm.gameon.data.collections.enums.TipologiaTerreno
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.Dropdown
import com.univpm.gameon.ui.components.OutlinedInputField

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
    val disponibilita = remember {
        mutableStateOf(campoDaModificare?.disponibilitaSettimanale?.toMutableList() ?: mutableListOf())
    }

    var showOrarioDialog by remember { mutableStateOf(false) }

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
                color = Color.Transparent,
                modifier = Modifier.heightIn(max = 600.dp) // imposta un'altezza max per attivare lo scroll
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomText(
                        text = if (campoDaModificare != null) "Modifica campo:" else "Aggiungi un nuovo campo:",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontFamily = lemonMilkFontFamily
                        )
                    )

                    if (showOrarioDialog) {
                        OrarioDialog(
                            onOrarioAdded = {
                                disponibilita.value.add(it)
                                showOrarioDialog = false
                            },
                            onDismiss = { showOrarioDialog = false },
                        )
                    }

                    OutlinedInputField(value = nome, onValueChange = { nome = it }, label = "Nome campo")
                    OutlinedInputField(value = prezzo, onValueChange = { prezzo = it }, label = "Prezzo/h")
                    OutlinedInputField(value = numGiocatori, onValueChange = { numGiocatori = it }, label = "Num. giocatori")

                    Dropdown(
                        current = sport,
                        options = Sport.entries,
                        getLabel = { it.label },
                        label = "Sport",
                        onSelected = { sport = it }
                    )

                    Dropdown(
                        current = terreno,
                        options = TipologiaTerreno.entries,
                        getLabel = { it.label },
                        label = "Terreno",
                        onSelected = { terreno = it }
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = spogliatoi,
                            onCheckedChange = { spogliatoi = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFCFFF5E),
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.Black
                            )
                        )
                        CustomText(
                            text = "Spogliatoi disponibili",
                            color = Color.White,
                            fontFamily = futuraBookFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    if (disponibilita.value.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1C1C1C), RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            CustomText(text = "Orari settimanali:", color = Color.White, fontFamily = futuraBookFontFamily)
                            disponibilita.value.forEach { orario ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CustomText(
                                        text = "${giornoLabel(orario.giornoSettimana)} ${orario.orarioApertura} - ${orario.orarioChiusura}",
                                        color = Color.White,
                                        fontFamily = futuraBookFontFamily
                                    )
                                    Button(
                                        onClick = {
                                            disponibilita.value = disponibilita.value.toMutableList().also {
                                                it.remove(orario)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                    ) {
                                        Text("X", color = Color.White)
                                    }
                                }
                            }
                        }
                    }

                    ButtonComponent(
                        text = "+ Aggiungi orario",
                        onClick = { showOrarioDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ButtonComponent(
                        text = if (campoDaModificare != null) "Modifica campo" else "Aggiungi campo",
                        onClick = {
                            onCampoAdded(
                                Campo(
                                    nomeCampo = nome,
                                    sport = sport,
                                    tipologiaTerreno = terreno,
                                    prezzoOrario = prezzo.toDoubleOrNull() ?: 0.0,
                                    numeroGiocatori = numGiocatori.toIntOrNull() ?: 0,
                                    spogliatoi = spogliatoi,
                                    disponibilitaSettimanale = disponibilita.value
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
