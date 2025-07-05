package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
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
import com.univpm.gameon.ui.components.ButtonComponent


@Composable
fun AdminHomeScreen(navController: NavController) {
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
            ){
                RoundedButtonComponent(
                text = "Gestione Account",
                onClick = { navController.navigate(EditProfileScreenRoute) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
            )
                RoundedButtonComponent(
                    text = "Gestione Prenotazioni",
                    onClick = { navController.navigate(GestionePrenotazioniAdminRoute) },
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
            ){
                RoundedButtonComponent(
                    text = "Gestione Strutture",
                    onClick = { navController.navigate(StruttureListRoute) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                )
                RoundedButtonComponent(
                    text = "Visualizza Messaggi",
                    onClick = { navController.navigate(ChatListAdminScreenRoute) },
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
            ){
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
                CustomText(text = "Elimina Account", fontSize = 16.sp, color = Color.White)
            }


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
