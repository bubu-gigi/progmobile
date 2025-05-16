package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.univpm.gameon.data.collections.enums.UserRuolo

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var codiceFiscale by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var ruolo by remember { mutableStateOf(UserRuolo.Giocatore) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrazione", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") }
        )

        OutlinedTextField(
            value = cognome,
            onValueChange = { cognome = it },
            label = { Text("Cognome") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        OutlinedTextField(
            value = codiceFiscale,
            onValueChange = { codiceFiscale = it },
            label = { Text("Codice Fiscale") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // TODO: handleRegister(User(...))
        }) {
            Text("Registrati")
        }
    }
}
