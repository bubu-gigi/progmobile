package com.univpm.gameon.ui.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatScreenRoute
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                            .border(
                                BorderStroke(3.dp, Color(0xFFE36BE0)),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD3D3D3))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        ChatScreenRoute(
                                            conv.strutturaId,
                                            conv.strutturaNome,
                                            conv.giocatoreId
                                        )
                                    )
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    CustomText(
                                        text = "Struttura: ${conv.strutturaNome}",
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                    CustomText(
                                        text = "Giocatore: ${conv.giocatoreNome}",
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                    CustomText(
                                        text = "Ultimo messaggio: ${conv.ultimoMessaggio}",
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
