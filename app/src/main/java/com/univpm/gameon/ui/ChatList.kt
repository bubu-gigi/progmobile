package com.univpm.gameon.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.Conversazione
import com.univpm.gameon.viewmodels.ChatListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun ChatListScreen(
    navController: NavController
) {
    val viewModel: ChatListViewModel = hiltViewModel()
    val conversazioni by viewModel.conversazioni.collectAsState()

    LaunchedEffect(Unit) {
        val userId = UserSessionManager.userId
        if (!userId.isNullOrBlank()) {
            viewModel.loadConversazioniForUser(userId)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Le tue conversazioni",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(conversazioni) { conv ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Struttura: ${conv.strutturaId}")
                        Text("Ultimo messaggio: ${conv.ultimoMessaggio}")
                    }
                }
            }
        }

        MappaStruttureConFiltri(
            strutture = struttureMock,
            onStrutturaSelezionata = { struttura ->
                Log.d("SELEZIONATA", "Hai cliccato su: ${struttura.nome}")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Inizia nuova conversazione")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatListScreenPreviewSimple() {
    val conversazioni = listOf(
        Conversazione(strutturaId = "Struttura A", giocatoreId = "User1", ultimoMessaggio = "Ciao!"),
        Conversazione(strutturaId = "Struttura B", giocatoreId = "User1", ultimoMessaggio = "A domani")
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Le tue conversazioni",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(conversazioni) { conv ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Struttura: ${conv.strutturaId}")
                        Text("Ultimo messaggio: ${conv.ultimoMessaggio}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Inizia nuova conversazione")
        }
    }
}

