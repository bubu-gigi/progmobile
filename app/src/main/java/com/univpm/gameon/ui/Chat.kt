package com.univpm.gameon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val listState = rememberLazyListState()

    // Scroll automatico all’ultimo messaggio (che è all’inizio della lista)
    LaunchedEffect(messaggi.size) {
        if (messaggi.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Chat con $strutturaNome",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            reverseLayout = true
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
                            mittente = giocatoreId
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
    val isStrutturaMessage = !isOwnMessage && messaggio.mittente.startsWith("struttura_")

    val alignment = when {
        isOwnMessage -> Arrangement.End
        else -> Arrangement.Start
    }

    val bgColor = when {
        isOwnMessage -> MaterialTheme.colorScheme.primaryContainer
        isStrutturaMessage -> Color(0xFFD0E8FF) // Azzurro chiaro per messaggi della struttura
        else -> MaterialTheme.colorScheme.secondaryContainer
    }

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
