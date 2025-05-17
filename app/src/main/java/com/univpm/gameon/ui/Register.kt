package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val registerViewModel: RegisterViewModel = viewModel()

    var name by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var codiceFiscale by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            val user = User(
                name = name,
                cognome = cognome,
                email = email,
                codiceFiscale = codiceFiscale,
                password = password
            )
            registerViewModel.registerUser(user)
        }) {
            Text("Registrati")
        }

        registerViewModel.registrationState.value?.let {
            if (it == "SUCCESS") {
                navController.navigate(LoginScreenRoute)
            } else {
                Text("Errore: $it", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}


@Composable
fun RegisterScreenContent(
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
    onRegisterClick: () -> Unit,
    errorMessage: String? = null
) {
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
            onValueChange = onNameChange,
            label = { Text("Nome") }
        )

        OutlinedTextField(
            value = cognome,
            onValueChange = onCognomeChange,
            label = { Text("Cognome") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") }
        )

        OutlinedTextField(
            value = codiceFiscale,
            onValueChange = onCodiceFiscaleChange,
            label = { Text("Codice Fiscale") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRegisterClick) {
            Text("Registrati")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenContentPreview() {
    RegisterScreenContent(
        name = "Mario",
        cognome = "Rossi",
        email = "mario.rossi@example.com",
        codiceFiscale = "MRARSS99A01H501Z",
        password = "password123",
        onNameChange = {},
        onCognomeChange = {},
        onEmailChange = {},
        onCodiceFiscaleChange = {},
        onPasswordChange = {},
        onRegisterClick = {},
        errorMessage = "Email già registrata"
    )
}

