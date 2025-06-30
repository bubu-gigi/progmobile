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

            OutlinedInputField(value = holderName, onValueChange = { holderName = it }, label = "Intestatario")
            Spacer(Modifier.height(8.dp))
            OutlinedInputField(value = cardNumber, onValueChange = { cardNumber = it }, label = "Numero carta")
            Spacer(Modifier.height(8.dp))
            OutlinedInputField(value = cvv, onValueChange = { cvv = it }, label = "CVV")
            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedInputField(value = month, onValueChange = { month = it }, label = "Mese")
                OutlinedInputField(value = year, onValueChange = { year = it }, label = "Anno")
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
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}