package com.univpm.gameon.ui

import android.R.attr.fontStyle
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .outerBorder(color = Color.Blue, strokeWidth = 9.dp, cornerRadius = 0.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ok),
            contentDescription = "Immagine di accesso",
            modifier = Modifier
                .size(150.dp)
                .outerBorder(color = Color.Blue, strokeWidth = 165.dp, cornerRadius = 0.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(100.dp))
        Text("Benvenuto, Admin!",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,       // puoi cambiare la dimensione
                letterSpacing = 2.sp    // spaziatura tra le lettere
            ))

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red, // rosso
                contentColor = Color.White           // colore del testo
        )) {
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminHomeContentPreview() {
    AdminHomeContent(onLogout = {})
}
