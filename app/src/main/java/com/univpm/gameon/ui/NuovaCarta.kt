package com.univpm.gameon.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.data.collections.enums.CardProvider
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Inserisci nuova carta", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = holderName,
            onValueChange = { holderName = it },
            label = { Text("Intestatario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Numero carta") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("CVV") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = month,
                onValueChange = { month = it },
                label = { Text("Mese") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Anno") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        DropdownMenuProviderSelector(provider) { selected ->
            provider = selected
        }

        Spacer(Modifier.height(24.dp))

        Button(onClick = {
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
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Salva carta")
        }
    }
}

@Composable
fun DropdownMenuProviderSelector(current: CardProvider, onSelected: (CardProvider) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = current.name,
            onValueChange = {},
            label = { Text("Circuito") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = true }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            CardProvider.entries.forEach { provider ->
                DropdownMenuItem(
                    text = { Text(provider.name) },
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
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Inserisci nuova carta", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = holderName,
            onValueChange = onHolderNameChange,
            label = { Text("Intestatario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = onCardNumberChange,
            label = { Text("Numero carta") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cvv,
            onValueChange = onCvvChange,
            label = { Text("CVV") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = month,
                onValueChange = onMonthChange,
                label = { Text("Mese") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = year,
                onValueChange = onYearChange,
                label = { Text("Anno") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        DropdownMenuProviderSelector(current = provider, onSelected = onProviderSelected)

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salva carta")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NuovaCartaContentPreview() {
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
