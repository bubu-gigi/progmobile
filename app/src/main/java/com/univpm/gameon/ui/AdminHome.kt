package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatListAdminScreenRoute
import com.univpm.gameon.core.EditProfileScreenRoute
import com.univpm.gameon.core.GestionePrenotazioniAdminRoute
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.StruttureListRoute
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.RoundedButtonComponent
import com.univpm.gameon.viewmodels.AuthViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip

import androidx.compose.ui.text.style.TextAlign


@Composable
fun AdminHomeScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val showDeleteDialog = remember { mutableStateOf(false) }

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
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(170.dp))
            CustomText(text = "BENVENUTO!", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))

            RoundedButtonComponent(
                text = "Gestione Account",
                onClick = { navController.navigate(EditProfileScreenRoute) }
            )
            Spacer(modifier = Modifier.height(4.dp))

            RoundedButtonComponent(
                text = "Gestione Prenotazioni",
                onClick = { navController.navigate(GestionePrenotazioniAdminRoute) }
            )
            Spacer(modifier = Modifier.height(4.dp))

            RoundedButtonComponent(
                text = "Gestione Strutture",
                onClick = { navController.navigate(StruttureListRoute) }
            )
            Spacer(modifier = Modifier.height(4.dp))

            RoundedButtonComponent(
                text = "Visualizza Messaggi",
                onClick = { navController.navigate(ChatListAdminScreenRoute) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Pulsante Elimina Account
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .border(BorderStroke(2.dp, Color.Red), shape = RoundedCornerShape(12.dp))
                    .background(color = Color(0xFF440000), shape = RoundedCornerShape(12.dp))
                    .clickable(onClick = { showDeleteDialog.value = true })
                    .padding(vertical = 15.dp, horizontal = 32.dp)
            ) {
                CustomText(text = "Elimina Account", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Pulsante Logout
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(70.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(16.dp))
                    .background(color = Color(0xFF440000))
                    .clickable(onClick = { showDeleteDialog.value = true }),
                contentAlignment = Alignment.Center
            ) {
                CustomText(text = "Logout", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
