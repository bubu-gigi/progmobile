package com.univpm.gameon.ui.carte

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.data.collections.enums.CardProvider
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.Dropdown
import com.univpm.gameon.ui.components.OutlinedInputField
import com.univpm.gameon.viewmodels.CarteViewModel

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

    var holderNameError by remember { mutableStateOf<String?>(null) }
    var cardNumberError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }
    var monthError by remember { mutableStateOf<String?>(null) }
    var yearError by remember { mutableStateOf<String?>(null) }

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
                text = "Inserisci nuova carta:",
                fontSize = 23.sp
            )

            Spacer(Modifier.height(16.dp))

            OutlinedInputField(
                value = holderName,
                onValueChange = { holderName = it },
                label = "Intestatario",
                errorText = holderNameError
            )

            Spacer(Modifier.height(8.dp))

            OutlinedInputField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = "Numero carta",
                errorText = cardNumberError
            )

            Spacer(Modifier.height(8.dp))

            OutlinedInputField(
                value = cvv,
                onValueChange = { cvv = it },
                label = "CVV",
                errorText = cvvError
            )

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedInputField(
                    value = month,
                    onValueChange = { month = it },
                    label = "Mese",
                    errorText = monthError,
                    modifier = Modifier.weight(1f)
                )
                OutlinedInputField(
                    value = year,
                    onValueChange = { year = it },
                    label = "Anno",
                    errorText = yearError,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))

            Dropdown(
                current = provider,
                onSelected = { provider = it },
                options = CardProvider.entries,
                getLabel = { it.label },
                label = "Circuito"
            )

            Spacer(Modifier.height(24.dp))

            ButtonComponent(
                text = "Salva carta",
                onClick = {
                    holderNameError = if (holderName.isBlank()) "Campo obbligatorio" else null
                    cardNumberError = if (!cardNumber.matches(Regex("\\d{16}"))) "Numero non valido" else null
                    cvvError = if (!cvv.matches(Regex("\\d{3}"))) "CVV non valido" else null
                    monthError = month.toIntOrNull()?.let { if (it in 1..12) null else "Mese non valido" } ?: "Mese obbligatorio"
                    yearError = year.toIntOrNull()?.let { if (it >= 2024) null else "Anno non valido" } ?: "Anno obbligatorio"

                    val isValid = listOf(
                        holderNameError,
                        cardNumberError,
                        cvvError,
                        monthError,
                        yearError
                    ).all { it == null }

                    if (isValid) {
                        val carta = Carta(
                            id = System.currentTimeMillis().toString(),
                            userId = userId,
                            cardHolderName = holderName,
                            cardNumber = cardNumber,
                            expirationMonth = month.toInt(),
                            expirationYear = year.toInt(),
                            cvv = cvv,
                            provider = provider
                        )
                        viewModel.salvaCarta(carta)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
