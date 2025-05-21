package com.univpm.gameon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterEditProfileForm(
    title: String,
    name: String,
    cognome: String,
    email: String,
    codiceFiscale: String,
    password: String,
    onNameChange: (String) -> Unit,
    onCognomeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCodiceFiscaleChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onActionClick: () -> Unit,
    actionButtonText: String,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Nome") })
        OutlinedTextField(value = cognome, onValueChange = onCognomeChange, label = { Text("Cognome") })
        OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") })
        OutlinedTextField(value = codiceFiscale, onValueChange = onCodiceFiscaleChange, label = { Text("Codice Fiscale") })
        OutlinedTextField(value = password, onValueChange = onPasswordChange, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onActionClick) {
            Text(actionButtonText)
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun RegisterEditProfileContent(
    title: String,
    name: String,
    cognome: String,
    email: String,
    codiceFiscale: String,
    password: String,
    onNameChange: (String) -> Unit,
    onCognomeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCodiceFiscaleChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onActionClick: () -> Unit,
    actionButtonText: String,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Nome") })
        OutlinedTextField(value = cognome, onValueChange = onCognomeChange, label = { Text("Cognome") })
        OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") })
        OutlinedTextField(value = codiceFiscale, onValueChange = onCodiceFiscaleChange, label = { Text("Codice Fiscale") })
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onActionClick) {
            Text(actionButtonText)
        }


        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterEditProfileContentPreview() {
    RegisterEditProfileContent(
        title = "Registrazione",
        name = "Mario",
        cognome = "Rossi",
        email = "mario.rossi@email.com",
        codiceFiscale = "RSSMRA80A01H501U",
        password = "password123",
        onNameChange = {},
        onCognomeChange = {},
        onEmailChange = {},
        onCodiceFiscaleChange = {},
        onPasswordChange = {},
        onActionClick = {},
        actionButtonText = "Registrati",
        errorMessage = null
    )
}
