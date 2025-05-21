package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

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
    errorMessage: String? = null,
    fontSize: TextUnit,
    color: Color
) {
    val borderColor = Color(0xFFE36BE0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nome") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = borderColor,
                unfocusedLabelColor = borderColor,
                cursorColor = borderColor
            )
        )

        OutlinedTextField(
            value = cognome,
            onValueChange = onCognomeChange,
            label = { Text("Cognome") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = borderColor,
                unfocusedLabelColor = borderColor,
                cursorColor = borderColor
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = borderColor,
                unfocusedLabelColor = borderColor,
                cursorColor = borderColor
            )
        )

        OutlinedTextField(
            value = codiceFiscale,
            onValueChange = onCodiceFiscaleChange,
            label = { Text("Codice Fiscale") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = borderColor,
                unfocusedLabelColor = borderColor,
                cursorColor = borderColor
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = borderColor,
                unfocusedLabelColor = borderColor,
                cursorColor = borderColor
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onActionClick,
            modifier = Modifier
                .padding(18.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6136FF))
        ) {
            Text(
                text = "Salva Modifiche",
                color = color,
                fontSize = fontSize
            )
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
    RegisterEditProfileForm(
        title = title,
        name = name,
        cognome = cognome,
        email = email,
        codiceFiscale = codiceFiscale,
        password = password,
        onNameChange = onNameChange,
        onCognomeChange = onCognomeChange,
        onEmailChange = onEmailChange,
        onCodiceFiscaleChange = onCodiceFiscaleChange,
        onPasswordChange = onPasswordChange,
        onActionClick = onActionClick,
        actionButtonText = actionButtonText,
        errorMessage = errorMessage,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        color = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterEditProfileContentPreview() {
    RegisterEditProfileContent(
        title = "Modifica Profilo",
        name = "Mario",
        cognome = "Rossi",
        email = "mario.rossi@email.it",
        codiceFiscale = "RSSMRA80A01H501U",
        password = "password123",
        onNameChange = {},
        onCognomeChange = {},
        onEmailChange = {},
        onCodiceFiscaleChange = {},
        onPasswordChange = {},
        onActionClick = {},
        actionButtonText = "Salva Modifiche",
        errorMessage = null
    )
}

