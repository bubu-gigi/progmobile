package com.univpm.gameon.ui.struttura

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.univpm.gameon.R
import com.univpm.gameon.core.giornoLabel
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.enums.Sport
import com.univpm.gameon.data.collections.enums.TipologiaTerreno
import com.univpm.gameon.ui.futuraBookFontFamily
import com.univpm.gameon.ui.lemonMilkFontFamily


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
    val disponibilita = remember {
        mutableStateOf(
            campoDaModificare?.disponibilitaSettimanale?.toMutableList() ?: mutableListOf()
        )
    }


    val borderColor = Color(0xFFE36BE0)
    val containerColor = Color(0xFF2B2B2B)
    var menuSportEspanso by remember { mutableStateOf(false) }
    var menuTerrenoEspanso by remember { mutableStateOf(false) }
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

                    if (showOrarioDialog) {
                        OrarioDialog(
                            onOrarioAdded = {
                                disponibilita.value.add(it)
                                showOrarioDialog = false
                            },
                            onDismiss = { showOrarioDialog = false },
                        )
                    }

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
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuSportEspanso)
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
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuTerrenoEspanso)
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

                    if (disponibilita.value.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1C1C1C), RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Text("Orari settimanali:", color = Color.White, fontFamily = futuraBookFontFamily)
                            disponibilita.value.forEach { orario ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
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

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { showOrarioDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color.Yellow), RoundedCornerShape(12.dp))
                            .background(Color(0xFF444444), RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Text(
                            text = "+ Aggiungi orario",
                            color = Color.Yellow,
                            fontFamily = futuraBookFontFamily
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
                                    disponibilitaSettimanale = disponibilita.value
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