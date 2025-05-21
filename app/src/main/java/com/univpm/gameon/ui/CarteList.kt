package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.core.NuovaCartaScreenRoute
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.viewmodels.CarteViewModel
import androidx.compose.ui.text.TextStyle


@Composable
fun CarteListScreen(navController: NavController) {

    val carteViewModel: CarteViewModel = hiltViewModel()
    val userId = UserSessionManager.userId

    LaunchedEffect(Unit) {
        userId?.let {
            carteViewModel.caricaCartePerUtente(it)
        }
    }

    val carte by carteViewModel.carte.collectAsState()
    val errore by carteViewModel.errore.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {


        Text("Le tue carte")


        Spacer(modifier = Modifier.height(16.dp))

        errore?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        LazyColumn {
            items(carte) { carta ->
                CartaItem(
                    carta = carta,
                    onDelete = {
                        userId?.let { uid ->
                            carteViewModel.eliminaCarta(carta.id, uid)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate(NuovaCartaScreenRoute)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inserisci nuova carta")
        }

    }
}

@Composable
fun CartaItem(carta: Carta, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
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
                Text(text = carta.cardHolderName, style = MaterialTheme.typography.titleMedium)
                Text(text = "**** **** **** ${carta.cardNumber.takeLast(4)}")
                Text(text = "${carta.expirationMonth}/${carta.expirationYear}")
            }

            Button(
                onClick = onDelete,
                modifier = Modifier
                    .padding(18.dp)
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                    .background(
                        color = Color(0xFF6136FF),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Text(
                    text = "Elimina",
                    color = Color(0xFFCFFF5E),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CarteListContent(
    carte: List<Carta>,
    errore: String?,
    onDelete: (String) -> Unit,
    onAdd: () -> Unit
) { Box(
    modifier = Modifier
        .fillMaxSize()
) {// SFONDO IMMAGINE
    Image(
        painter = painterResource(id = R.drawable.sfondocarta),
        contentDescription = "Sfondo",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Spacer(modifier = Modifier.height(190.dp))
        Text(
            text = "Le tue carte:",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = futuraBookFontFamily
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        errore?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        LazyColumn {
            items(carte) { carta ->
                CartaItem(
                    carta = carta,
                    onDelete = { onDelete(carta.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAdd,
            modifier = Modifier
                .padding(8.dp)
                .size(width = 500.dp, height = 50.dp)
                .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(232323),
                contentColor = Color.White
        )



        ) {
            Text(
                text = "Inserisci nuova carta",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = futuraBookFontFamily
            )
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun CarteListContentPreview() {
    val dummyCards = listOf(
        Carta(
            id = "1",
            userId = "u1",
            cardHolderName = "Mario Rossi",
            cardNumber = "1111222233334444",
            expirationMonth = 12,
            expirationYear = 2026,
            cvv = "123"
        )
    )

    // Applica il MaterialTheme e imposta futuraBookFontFamily per tutti gli stili
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
        Surface { // La Surface assicura che il tema sia applicato al contenuto
            CarteListContent(
                carte = dummyCards,
                errore = null,
                onDelete = {},
                onAdd = {}
            )
        }
    }
}


