package com.univpm.gameon.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.univpm.gameon.data.collections.Recensione
import com.univpm.gameon.viewmodels.RecensioneViewModel

@Composable
fun RecensioneForm(
    strutturaId: String,
    utenteId: String,
    viewModel: RecensioneViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var rating by remember { mutableStateOf(0) }
    var commento by remember { mutableStateOf("") }
    var recensioneInviata by remember { mutableStateOf(false) }
    var haGiaRecensito by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }

    // Controllo iniziale: ha già recensito?
    LaunchedEffect(Unit) {
        viewModel.haGiaRecensito(strutturaId, utenteId) { esiste ->
            haGiaRecensito = esiste
            loading = false
        }
    }

    when {
        loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        haGiaRecensito || recensioneInviata -> {
            Text(
                text = "Hai già lasciato una recensione per questa struttura.",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        else -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Lascia una recensione", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    (1..5).forEach { index ->
                        IconToggleButton(
                            checked = rating >= index,
                            onCheckedChange = { rating = index }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Stella $index",
                                tint = if (rating >= index) Color.Yellow else Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = commento,
                    onValueChange = { commento = it },
                    placeholder = { Text("Scrivi un commento...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (rating > 0) {
                            val nuovaRecensione = Recensione(
                                strutturaId = strutturaId,
                                utenteId = utenteId,
                                rating = rating,
                                commento = commento
                            )
                            viewModel.inviaRecensione(nuovaRecensione) {
                                recensioneInviata = true
                                Toast.makeText(
                                    context,
                                    "Recensione inviata!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(context, "Dai almeno 1 stella", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Invia Recensione")
                }
            }
        }
    }
}
