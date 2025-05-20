package com.univpm.gameon.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.core.DettaglioCartaScreenRoute
import com.univpm.gameon.core.NuovaCartaScreenRoute
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.viewmodels.CarteViewModel

@Composable
fun CarteListScreen(navController: NavController) {
    val carteViewModel: CarteViewModel = hiltViewModel()
    val userId = UserSessionManager.userId

    LaunchedEffect(Unit) {
        userId?.let {
            carteViewModel.caricaCartePerUtente(it)
        }
    }

    val carte by carteViewModel.carte.collectAsState()
    val errore by carteViewModel.errore.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Le tue carte",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        errore?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        LazyColumn {
            items(carte) { carta ->
                CartaItem(
                    carta = carta,
                    onClick = {
                        navController.navigate(DettaglioCartaScreenRoute(carta.id))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate(NuovaCartaScreenRoute)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inserisci nuova carta")
        }

    }
}

@Composable
fun CartaItem(carta: Carta, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = carta.cardHolderName, style = MaterialTheme.typography.titleMedium)
            Text(text = "**** **** **** ${carta.cardNumber.takeLast(4)}")
            Text(text = "${carta.expirationMonth}/${carta.expirationYear}")
        }
    }
}
