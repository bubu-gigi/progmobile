package com.univpm.gameon.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatScreenRoute
import com.univpm.gameon.viewmodels.StruttureViewModel


@Composable
fun ChatListScreen(
    navController: NavController
) {
    val viewModel: ChatListViewModel = hiltViewModel()
    val struttureViewModel: StruttureViewModel = hiltViewModel()

    val conversazioni by viewModel.conversazioni.collectAsState()
    val strutture by struttureViewModel.strutture.collectAsState()

    LaunchedEffect(Unit) {
        val userId = UserSessionManager.userId
        if (!userId.isNullOrBlank()) {
            viewModel.loadConversazioniForUser(userId)
        }
        struttureViewModel.caricaStrutture()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.sfondocarta),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
        ) {
            Spacer(modifier = Modifier.height(210.dp))
            Text(
                text = "Le tue conversazioni:",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontSize = 23.sp,
                    fontFamily = lemonMilkFontFamily
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(conversazioni) { conv ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                            .border(BorderStroke(3.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(12.dp)),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD3D3D3))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Struttura: ${conv.strutturaId}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Ultimo messaggio: ${conv.ultimoMessaggio}",
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            MappaStruttureConFiltri(
                strutture = strutture,
                onStrutturaSelezionata = { struttura ->
                   navController.navigate(ChatScreenRoute(struttura.id, struttura.nome, null))
                },
                height = 300.dp,
                width = Dp.Unspecified
            )
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

    MaterialTheme(
        typography = Typography(
            displayLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 57.sp),
            displayMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 45.sp),
            displaySmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 36.sp),

            headlineLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 32.sp),
            headlineMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 28.sp),
            headlineSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 24.sp),

            titleLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 22.sp),
            titleMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium),
            titleSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium),

            bodyLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp),
            bodyMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp),
            bodySmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 12.sp),

            labelLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium),
            labelMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 12.sp, fontWeight = FontWeight.Medium),
            labelSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 11.sp, fontWeight = FontWeight.Medium),
        )
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sfondocarta),
                    contentDescription = "Sfondo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
                ) {
                    Spacer(modifier = Modifier.height(210.dp))
                    Text(
                        text = "Le tue conversazioni:",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White,
                            fontSize = 23.sp,
                            fontFamily = lemonMilkFontFamily
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(conversazioni) { conv ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
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
                                        Text(
                                            text = "Struttura: ${conv.strutturaId}",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "Ultimo messaggio: ${conv.ultimoMessaggio}",
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(width = 500.dp, height = 50.dp)
                            .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(120.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6136FF),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Inizia nuova conversazione",
                            color = Color(0xFFCFFF5E),
                            fontSize = 18.sp,
                            fontFamily = futuraBookFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

