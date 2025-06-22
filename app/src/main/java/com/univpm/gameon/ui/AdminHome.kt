package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatListAdminScreenRoute
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.StruttureListRoute
import com.univpm.gameon.viewmodels.AuthViewModel

//definizione dei font

val futuraBookFontFamily = FontFamily(
    Font(R.font.futura_book_font)
)
val lemonMilkFontFamily= FontFamily(
    Font(R.font.lemon_milk_bold)
)

@Composable
fun AdminHomeScreen(navController: NavController) {

    val authViewModel: AuthViewModel = hiltViewModel()

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
                "BENVENUTO!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontFamily = lemonMilkFontFamily,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
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
                Text(
                    text = "Gestione Account",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = futuraBookFontFamily)
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
                Text(
                    text = "Gestione Prenotazioni",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = futuraBookFontFamily)
            }

            Button(
                onClick = { navController.navigate(StruttureListRoute) },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)), // Bordo con angoli arrotondati
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(232323), // Corretto il colore in formato esadecimale
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Gestione Strutture",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = futuraBookFontFamily)
            }

            Button(
                onClick = { navController.navigate(ChatListAdminScreenRoute) },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)), // Bordo con angoli arrotondati
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(232323), // Corretto il colore in formato esadecimale
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Visualizza Messaggi",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = futuraBookFontFamily)
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
                    .clickable(onClick = {authViewModel.logout(); navController.navigate(
                        LoginScreenRoute)})
                    .padding(vertical = 15.dp, horizontal = 32.dp)
            ) {
                Text(
                    text = "Logout",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = futuraBookFontFamily
                )
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
                "BENVENUTO!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontFamily = lemonMilkFontFamily,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
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
                Text(
                    text = "Gestione Account",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = futuraBookFontFamily)
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
                Text(
                    text = "Gestione Prenotazioni",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = futuraBookFontFamily)
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
                Text(
                    text = "Visualizza Messaggi",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = futuraBookFontFamily)
            }


            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp)) // Aggiunto il bordo con angoli arrotondati
                    .background(
                        color = Color(0xFF6136FF),
                        shape = RoundedCornerShape(12.dp) // Assicurati che il background abbia la stessa forma
                    )
                    .clickable(onClick = onLogout)
                    .padding(vertical = 15.dp, horizontal = 32.dp)
            ) {
                Text(
                    text = "Logout",
                    color = Color(0xFFCFFF5E),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = futuraBookFontFamily

                )
            }

        }
        }
    }

//funzione di anteprima

@Preview(showBackground = true)
@Composable
fun AdminHomeContentPreview() {
    AdminHomeContent(onLogout = {})
}
