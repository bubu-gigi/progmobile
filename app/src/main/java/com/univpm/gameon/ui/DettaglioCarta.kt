package com.univpm.gameon.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DettaglioCartaScreen(navController: NavController, cartaId: String) {
    Text("Dettaglio della carta con ID: $cartaId")
}
