package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.univpm.gameon.core.checkAccess
import com.univpm.gameon.core.logout
import com.univpm.gameon.data.collections.enums.UserRuolo
import com.univpm.gameon.R

@Composable
fun AdminHomeScreen(navController: NavController) {
    checkAccess(navController, UserRuolo.Admin)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Benvenuto, Admin!", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { logout(navController) }) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun AdminHomeContent(onLogout: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // SFONDO IMMAGINE
        Image(
            painter = painterResource(id = R.drawable.sfondobase),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // CONTENUTO SOPRA LO SFONDO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Rimosso il modificatore outerBorder
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(470.dp))


            Text(
                "Benvenuto, Admin!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* Azione quando il bottone viene premuto */ },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)), // Bordo con angoli arrotondati
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(232323), // Corretto il colore in formato esadecimale
                contentColor = Color.White
            )
            ) {
                Text(text = "Gestione Account")
            }

            Button(
                onClick = { /* Azione quando il bottone viene premuto */ },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)), // Bordo con angoli arrotondati
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(232323), // Corretto il colore in formato esadecimale
                contentColor = Color.White
            )
            ) {
                Text(text = "Gestione Prenotazioni")
            }

            Button(
                onClick = { /* Azione quando il bottone viene premuto */ },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)), // Bordo con angoli arrotondati
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(232323), // Corretto il colore in formato esadecimale
                    contentColor = Color.White
                )
            ) {
                Text(text = "Visualizza Messaggi")
            }


            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(12.dp)) // Aggiunto il bordo con angoli arrotondati
                    .background(
                        color = Color(232323),
                        shape = RoundedCornerShape(12.dp) // Assicurati che il background abbia la stessa forma
                    )
                    .clickable(onClick = onLogout)
                    .padding(vertical = 15.dp, horizontal = 32.dp)
            ) {
                Text(
                    text = "Logout",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        }
    }


@Preview(showBackground = true)
@Composable
fun AdminHomeContentPreview() {
    AdminHomeContent(onLogout = {})
}
