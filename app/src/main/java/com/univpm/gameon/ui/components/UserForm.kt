package com.univpm.gameon.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.univpm.gameon.R

@Composable
fun UserForm(
    title: String,
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String?,
    cognome: String,
    onCognomeChange: (String) -> Unit,
    cognomeError: String?,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    codiceFiscale: String,
    onCodiceFiscaleChange: (String) -> Unit,
    cfError: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    submitButtonText: String,
    onSubmit: () -> Unit,
    errorMessage: String? = null
) {
    BackgroundScaffold(backgroundResId = R.drawable.sfondocarta) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedInputField(
                value = name,
                onValueChange = onNameChange,
                label = "Nome",
                errorText = nameError
            )
            OutlinedInputField(
                value = cognome,
                onValueChange = onCognomeChange,
                label = "Cognome",
                errorText = cognomeError
            )
            OutlinedInputField(
                value = email,
                onValueChange = onEmailChange,
                label = "Email",
                errorText = emailError
            )
            OutlinedInputField(
                value = codiceFiscale,
                onValueChange = onCodiceFiscaleChange,
                label = "Codice Fiscale",
                errorText = cfError
            )
            OutlinedInputField(
                value = password,
                onValueChange = onPasswordChange,
                label = "Password",
                errorText = passwordError,
                isPassword = true
            )

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(20.dp))

            ButtonComponent(text=submitButtonText, onClick = onSubmit)
        }
    }
}
