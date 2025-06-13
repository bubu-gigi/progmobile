package com.univpm.gameon.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import com.univpm.gameon.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.univpm.gameon.ui.futuraBookFontFamily
import com.univpm.gameon.ui.lemonMilkFontFamily

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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Immagine di sfondo
        Image(
            painter = painterResource(id = R.drawable.sfondocarta), // Sostituisci con il tuo ID immagine
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenuto del modulo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            Text("Modifica profilo",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontFamily = lemonMilkFontFamily,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold))

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
                    cursorColor = borderColor,
                    focusedTextColor = Color.White, // Colore del testo quando il campo è a fuoco
                    unfocusedTextColor = Color.White // Colore del testo quando il campo non è a fuoco
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
                    cursorColor = borderColor,
                    focusedTextColor = Color.White, // Colore del testo quando il campo è a fuoco
                    unfocusedTextColor = Color.White // Colore del testo quando il campo non è a fuoco
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
                    cursorColor = borderColor,
                    focusedTextColor = Color.White, // Colore del testo quando il campo è a fuoco
                    unfocusedTextColor = Color.White // Colore del testo quando il campo non è a fuoco
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
                    cursorColor = borderColor,
                    focusedTextColor = Color.White, // Colore del testo quando il campo è a fuoco
                    unfocusedTextColor = Color.White // Colore del testo quando il campo non è a fuoco
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
                    cursorColor = borderColor,
                    focusedTextColor = Color.White, // Colore del testo quando il campo è a fuoco
                    unfocusedTextColor = Color.White // Colore del testo quando il campo non è a fuoco
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp)) // Aggiunto il bordo con angoli arrotondati
                    .background(
                        color = Color(0xFF6136FF),
                        shape = RoundedCornerShape(12.dp) // Assicurati che il background abbia la stessa forma
                    )
                    .clickable(onClick = onActionClick)
                    .padding(vertical = 15.dp, horizontal = 32.dp)
            ) {
                Text(
                    text = "Salva le modifiche ",
                    color = Color(0xFFCFFF5E),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = futuraBookFontFamily

                )
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
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

