package com.univpm.gameon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.Messaggio
import com.univpm.gameon.viewmodels.ChatViewModel

@Composable
fun ChatScreen(
    navController: androidx.navigation.NavController,
    strutturaId: String,
    strutturaNome: String
) {
    val chatViewModel: ChatViewModel = hiltViewModel()

    val giocatoreId = UserSessionManager.userId ?: ""
    LaunchedEffect(Unit) {
        chatViewModel.caricaConversazione(giocatoreId, strutturaId)
    }

    val messaggi by chatViewModel.messaggi.collectAsState()
    val conversazione by chatViewModel.conversazione.collectAsState()
    var testoMessaggio by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Chat con $strutturaNome",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            reverseLayout = true // i messaggi più recenti in fondo
        ) {
            items(messaggi.reversed()) { messaggio ->
                MessaggioItem(messaggio = messaggio, currentUser = giocatoreId)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Divider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = testoMessaggio,
                onValueChange = { testoMessaggio = it },
                placeholder = { Text("Scrivi un messaggio...") },
                singleLine = true
            )

            Button(
                onClick = {
                    if (testoMessaggio.isNotBlank()) {
                        chatViewModel.inviaMessaggio(
                            giocatoreId = giocatoreId,
                            strutturaId = strutturaId,
                            testo = testoMessaggio,
                            mittente = giocatoreId // o usa altro se sei admin
                        )
                        testoMessaggio = ""
                    }
                }
            ) {
                Text("Invia")
            }
        }
    }
}

@Composable
fun MessaggioItem(messaggio: Messaggio, currentUser: String) {
    val isOwnMessage = messaggio.mittente == currentUser
    val alignment = if (isOwnMessage) Arrangement.End else Arrangement.Start
    val bgColor = if (isOwnMessage) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = alignment
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .background(bgColor, shape = MaterialTheme.shapes.medium)
                .padding(8.dp)
        ) {
            Text(text = messaggio.testo)
        }
    }
}
