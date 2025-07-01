package com.univpm.gameon.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatScreenRoute
import com.univpm.gameon.ui.components.ConversazioneCard
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.SearchBar
import com.univpm.gameon.viewmodels.ChatViewModel

@Composable
fun ChatListAdminScreen(
    navController: NavController
) {
    val viewModel: ChatViewModel = hiltViewModel()
    val conversazioni by viewModel.conversazioni.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.caricaTutteLeConversazioni()
    }

    val filteredConversazioni = conversazioni.filter {
        it.strutturaNome.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sfondocarta),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)) {

            Spacer(modifier = Modifier.height(210.dp))

            CustomText(
                text = "Le tue conversazioni:",
                fontSize = 23.sp
            )

            SearchBar(query = searchQuery, onQueryChange = { searchQuery = it })

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredConversazioni) { conv ->
                    ConversazioneCard(
                        strutturaNome = conv.strutturaNome,
                        giocatoreNome = conv.giocatoreNome,
                        ultimoMessaggio = conv.ultimoMessaggio,
                        onClick = {
                            navController.navigate(ChatScreenRoute(conv.strutturaId, conv.strutturaNome, conv.giocatoreId))
                        }
                    )
                }
            }
        }
    }
}
