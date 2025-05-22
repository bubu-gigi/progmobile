package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.CarteListScreenRoute
import com.univpm.gameon.core.EditProfileScreenRoute
import com.univpm.gameon.core.checkAccess
import com.univpm.gameon.core.logout
import com.univpm.gameon.viewmodels.AuthViewModel
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatListScreenRoute

@Composable
fun GiocatoreHomeScreen(navController: NavController) {
    checkAccess(navController)

    val authViewModel: AuthViewModel = hiltViewModel()

    LaunchedEffect(authViewModel.destination.value) {
        authViewModel.destination.value?.let {
            navController.navigate(it)
            authViewModel.destination.value = null
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Benvenuto, Giocatore!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { logout(navController) }) {
            Text("Logout")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate(EditProfileScreenRoute) }) {
            Text("Modifica Profilo")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate(CarteListScreenRoute) }) {
            Text("Gestione Carte")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate(ChatListScreenRoute) }) {
            Text("Contatta Strutture")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { authViewModel.deleteAccount() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Elimina Account", color = MaterialTheme.colorScheme.onError)
        }
    }
}

@Composable
fun GiocatoreHomeContent(
    onLogout: () -> Unit,
    onEditProfile: () -> Unit,
    onGestioneCarte: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    // Sostituisci "background_image" con il nome esatto dell'immagine nella cartella drawable
    val background = painterResource(id = R.drawable.sfondobase)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Sfondo immagine
        Image(
            painter = background,
            contentDescription = null,
            contentScale = ContentScale.Crop, // Adatta l'immagine allo schermo
            modifier = Modifier.matchParentSize()
        )

        // Contenuto sovrapposto allo sfondo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(465.dp))
            Text(
                "BENVENUTO!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontFamily = lemonMilkFontFamily,
                    fontSize = 25.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onLogout() },
                    modifier = Modifier
                        .padding(7.dp)
                        .size(width = 183.dp, height = 50.dp)
                        .border(
                            BorderStroke(2.dp, Color(0xFFE36BE0)),
                            shape = RoundedCornerShape(120.dp)
                        ), // Bordo con angoli arrotondati
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(232323), // Corretto il colore in formato esadecimale
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Gestione Prenotazioni",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = futuraBookFontFamily)
                }

                Button(
                    onClick = { onEditProfile() },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 183.dp, height = 50.dp)
                        .border(
                            BorderStroke(2.dp, Color(0xFFE36BE0)),
                            shape = RoundedCornerShape(120.dp)
                        ), // Bordo con angoli arrotondati
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(232323), // Corretto il colore in formato esadecimale
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Modifica Profilo",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = futuraBookFontFamily)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onGestioneCarte() },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 183.dp, height = 50.dp)
                        .border(
                            BorderStroke(2.dp, Color(0xFFE36BE0)),
                            shape = RoundedCornerShape(120.dp)
                        ), // Bordo con angoli arrotondati
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(232323), // Corretto il colore in formato esadecimale
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Gestione Carte",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = futuraBookFontFamily)
                }

                Button(
                    onClick = { /* Azione quando il bottone viene premuto */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 183.dp, height = 50.dp)
                        .border(
                            BorderStroke(2.dp, Color(0xFFE36BE0)),
                            shape = RoundedCornerShape(120.dp)
                        ), // Bordo con angoli arrotondati
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(232323), // Corretto il colore in formato esadecimale
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Gestione Strutture",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = futuraBookFontFamily)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                                .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                                .background(
                                    color = Color(0xFF6136FF),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable(onClick = onDeleteAccount)
                                .padding(vertical = 15.dp)
                        ) {
                            Text(
                                text = "Elimina Account",
                                color = Color(0xFFCFFF5E),
                                fontSize = 18.sp,
                                fontFamily = futuraBookFontFamily,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                                .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                                .background(
                                    color = Color(0xFF6136FF),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable(onClick = onLogout)
                                .padding(vertical = 15.dp)
                        ) {
                            Text(
                                text = "Logout",
                                color = Color(0xFFCFFF5E),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = futuraBookFontFamily,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GiocatoreHomeContentPreview() {
    GiocatoreHomeContent(
        onLogout = {},
        onEditProfile = {},
        onGestioneCarte = {},
        onDeleteAccount = {}
    )
}
