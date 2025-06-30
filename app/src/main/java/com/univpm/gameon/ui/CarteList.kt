package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.core.NuovaCartaScreenRoute
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.viewmodels.CarteViewModel
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.CustomText


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
        CustomText(
            text = "Le tue carte:",
            fontSize = 23.sp
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
                    onDelete = {
                        userId?.let { uid ->
                            carteViewModel.eliminaCarta(carta.id, uid)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        ButtonComponent(
            text = "Inserisci nuova carta",
            onClick = {
                navController.navigate(NuovaCartaScreenRoute)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CartaItem(carta: Carta, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(BorderStroke(3.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD3D3D3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                CustomText(text = carta.cardHolderName, color = Color.Black)
                CustomText(text = "**** **** **** ${carta.cardNumber.takeLast(4)}", color = Color.Black)
                CustomText(text = "${carta.expirationMonth}/${carta.expirationYear}", color = Color.Black)
            }

            ButtonComponent(
                text = "Elimina",
                onClick = onDelete,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .widthIn(min = 100.dp)
            )
        }
    }
}
