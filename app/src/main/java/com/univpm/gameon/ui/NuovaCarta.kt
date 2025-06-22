package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.data.collections.enums.CardProvider
import com.univpm.gameon.viewmodels.CarteViewModel
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily


@Composable
fun NuovaCartaScreen(navController: NavController) {
    val viewModel: CarteViewModel = hiltViewModel()
    val userId = UserSessionManager.userId ?: return

    var holderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var provider by remember { mutableStateOf(CardProvider.VISA) }

    NuovaCartaContent(
        holderName = holderName,
        cardNumber = cardNumber,
        cvv = cvv,
        month = month,
        year = year,
        provider = provider,
        onHolderNameChange = { holderName = it },
        onCardNumberChange = { cardNumber = it },
        onCvvChange = { cvv = it },
        onMonthChange = { month = it },
        onYearChange = { year = it },
        onProviderSelected = { selected -> provider = selected },
        onSaveClick = {
            val carta = Carta(
                id = System.currentTimeMillis().toString(),
                userId = userId,
                cardHolderName = holderName,
                cardNumber = cardNumber,
                expirationMonth = month.toIntOrNull() ?: 1,
                expirationYear = year.toIntOrNull() ?: 2025,
                cvv = cvv,
                provider = provider
            )
            viewModel.salvaCarta(carta)
            navController.popBackStack()
        }
    )
}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuProviderSelector(
    current: CardProvider,
    onSelected: (CardProvider) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = current.label,
            onValueChange = {},
            readOnly = true,
            label = { Text("Circuito", color = Color.White, fontFamily = futuraBookFontFamily) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE36BE0),
                unfocusedBorderColor = Color(0xFFE36BE0),
                focusedLabelColor = Color(0xFFE36BE0),
                unfocusedLabelColor = Color(0xFFE36BE0),
                cursorColor = Color.Transparent
            ),
            modifier = Modifier
                .menuAnchor() // molto importante per il posizionamento del menu
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            CardProvider.entries.forEach { provider ->
                DropdownMenuItem(
                    text = {
                        Text(
                            provider.label,
                            color = Color.Black,
                            fontFamily = futuraBookFontFamily
                        )
                    },
                    onClick = {
                        onSelected(provider)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun NuovaCartaContent(
    holderName: String,
    cardNumber: String,
    cvv: String,
    month: String,
    year: String,
    provider: CardProvider,
    onHolderNameChange: (String) -> Unit,
    onCardNumberChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
    onProviderSelected: (CardProvider) -> Unit,
    onSaveClick: () -> Unit
) {
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
            .padding(18.dp)) {
            Spacer(modifier = Modifier.height(210.dp))
            Text(
                text = "Inserisci nuova carta:",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontSize = 23.sp,
                    fontFamily = lemonMilkFontFamily
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = holderName,
                onValueChange = onHolderNameChange,
                label = { Text("Intestatario", fontFamily = futuraBookFontFamily, color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE36BE0),
                    unfocusedBorderColor = Color(0xFFE36BE0),
                    focusedLabelColor = Color(0xFFE36BE0),
                    unfocusedLabelColor = Color(0xFFE36BE0),
                    cursorColor = Color.White
                )
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = cardNumber,
                onValueChange = onCardNumberChange,
                label = { Text("Numero carta", fontFamily = futuraBookFontFamily, color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE36BE0),
                    unfocusedBorderColor = Color(0xFFE36BE0),
                    focusedLabelColor = Color(0xFFE36BE0),
                    unfocusedLabelColor = Color(0xFFE36BE0),
                    cursorColor = Color.White
                )
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = cvv,
                onValueChange = onCvvChange,
                label = { Text("CVV", fontFamily = futuraBookFontFamily, color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE36BE0),
                    unfocusedBorderColor = Color(0xFFE36BE0),
                    focusedLabelColor = Color(0xFFE36BE0),
                    unfocusedLabelColor = Color(0xFFE36BE0),
                    cursorColor = Color.White
                )
            )
            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = month,
                    onValueChange = onMonthChange,
                    label = { Text("Mese", fontFamily = futuraBookFontFamily, color = Color.White) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE36BE0),
                        unfocusedBorderColor = Color(0xFFE36BE0),
                        focusedLabelColor = Color(0xFFE36BE0),
                        unfocusedLabelColor = Color(0xFFE36BE0),
                        cursorColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = year,
                    onValueChange = onYearChange,
                    label = { Text("Anno", fontFamily = futuraBookFontFamily, color = Color.White) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE36BE0),
                        unfocusedBorderColor = Color(0xFFE36BE0),
                        focusedLabelColor = Color(0xFFE36BE0),
                        unfocusedLabelColor = Color(0xFFE36BE0),
                        cursorColor = Color.White
                    )
                )
            }

            Spacer(Modifier.height(8.dp))

            DropdownMenuProviderSelector(current = provider, onSelected = onProviderSelected)

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 500.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(120.dp)), // Bordo verde chiaro e molto arrotondato
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6136FF), // Sfondo viola
                    contentColor = Color(0xFFCFFF5E) // Testo verde chiaro
                )
            ) {
                Text(
                    text = "Salva carta",
                    fontSize = 20.sp,
                    fontFamily = futuraBookFontFamily
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NuovaCartaContentPreview() {
    MaterialTheme(
        typography = Typography(
            displayLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 57.sp, color = Color.White),
            displayMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 45.sp, color = Color.White),
            displaySmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 36.sp, color = Color.White),

            headlineLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 32.sp, color = Color.White),
            headlineMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 28.sp, color = Color.White),
            headlineSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 24.sp, color = Color.White),

            titleLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 22.sp, color = Color.White),
            titleMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White),
            titleSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White),

            bodyLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
            bodyMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, color = Color.White),
            bodySmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 12.sp, color = Color.White),

            labelLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White),
            labelMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White),
            labelSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.White),
        )
    ) {
        Surface {
            NuovaCartaContent(
                holderName = "Mario Rossi",
                cardNumber = "1234123412341234",
                cvv = "123",
                month = "12",
                year = "2026",
                provider = CardProvider.VISA,
                onHolderNameChange = {},
                onCardNumberChange = {},
                onCvvChange = {},
                onMonthChange = {},
                onYearChange = {},
                onProviderSelected = {},
                onSaveClick = {}
            )
        }
    }
}