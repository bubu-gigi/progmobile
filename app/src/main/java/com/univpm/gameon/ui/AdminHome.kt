package com.univpm.gameon.ui

import android.R.attr.fontStyle
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.univpm.gameon.core.checkAccess
import com.univpm.gameon.core.logout
import com.univpm.gameon.data.collections.enums.UserRuolo
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.univpm.gameon.R

fun Modifier.outerBorder(
        color: Color, //Colore della cornice
        strokeWidth: Dp, //Spessore della cornice
        cornerRadius: Dp = 0.dp // Raggio di curvatura degli angoli (default è 0)
    ): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidthPx = strokeWidth.toPx()
        val halfStroke = strokeWidthPx / 2f

        val width = size.width
        val height = size.height

        drawRoundRect(
            color = color,
            topLeft = Offset(halfStroke, halfStroke),
            size = Size(width - strokeWidthPx, height - strokeWidthPx),
            style = Stroke(width = strokeWidthPx),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
)

@Composable
fun AdminHomeScreen(navController: NavController) {
    checkAccess(navController, UserRuolo.Admin)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .outerBorder(color = Color.Blue, strokeWidth = 4.dp, cornerRadius = 0.dp)
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        // SFONDO IMMAGINE
        Image(
            painter = painterResource(id = R.drawable.sfondo),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // CONTENUTO SOPRA LO SFONDO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .outerBorder(color = Color(0xFFC67C00), strokeWidth = 9.dp, cornerRadius = 0.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "GameON!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.ok),
                contentDescription = "Immagine di accesso",
                modifier = Modifier
                    .size(150.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                "Benvenuto, Admin!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { /* Azione quando il bottone viene premuto */ },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC67C00),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Gestione Account")
            }

            Button(
                onClick = { /* Azione quando il bottone viene premuto */ },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC67C00),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Gestione Prenotazioni")
            }

            Button(
                onClick = { /* Azione quando il bottone viene premuto */ },
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC67C00),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Visualizza Messaggi")
            }

            Spacer(modifier = Modifier.height(50.dp))




            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = MaterialTheme.shapes.medium,
                        ambientColor = Color.Black,
                        spotColor = Color.Gray
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFF44336), Color(0xFFb71c1c)) // gradiente rosso scuro
                        ),
                        shape = MaterialTheme.shapes.medium
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
