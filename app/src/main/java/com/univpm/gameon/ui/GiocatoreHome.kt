package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.CarteListScreenRoute
import com.univpm.gameon.core.EditProfileScreenRoute
import com.univpm.gameon.viewmodels.AuthViewModel
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatListScreenRoute
import com.univpm.gameon.core.GiocatorePrenotazioniRoute
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.RoundedButtonComponent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
// Aggiungi questi se non già presenti
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton

@Composable
fun GiocatoreHomeScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val destination by authViewModel.destination.collectAsState()
    val showDeleteDialog = remember { mutableStateOf(false) }

    LaunchedEffect(destination) {
        destination?.let {
            navController.navigate(it)
            authViewModel.clearDestination()
        }
    }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text(text = "Conferma Eliminazione") },
            text = {
                Text(
                    text = "Sei sicuro di voler eliminare il tuo account? Questa azione è irreversibile.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog.value = false
                        authViewModel.deleteAccount()
                    }
                ) {
                    Text("Conferma")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog.value = false }
                ) {
                    Text("Annulla")
                }
            }
        )
    }

    BackgroundScaffold(backgroundResId = R.drawable.sfondobase) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(400.dp))

            CustomText(
                text = "BENVENUTO!",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RoundedButtonComponent(
                    text = "Gestione Prenotazioni",
                    onClick = { navController.navigate(GiocatorePrenotazioniRoute) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                )
                RoundedButtonComponent(
                    text = "Modifica Profilo",
                    onClick = { navController.navigate(EditProfileScreenRoute) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RoundedButtonComponent(
                    text = "Gestione Carte",
                    onClick = { navController.navigate(CarteListScreenRoute) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                )
                RoundedButtonComponent(
                    text = "Contatta Strutture",
                    onClick = { navController.navigate(ChatListScreenRoute) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ButtonComponent(
                    text = "Elimina Account",
                    onClick = { showDeleteDialog.value = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                ButtonComponent(
                    text = "Logout",
                    onClick = { authViewModel.logout() },
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
    }
}

