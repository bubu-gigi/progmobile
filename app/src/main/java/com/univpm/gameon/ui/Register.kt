package com.univpm.gameon.ui

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.checkFieldLength
import com.univpm.gameon.core.validateCodiceFiscale
import com.univpm.gameon.core.validateEmail
import com.univpm.gameon.core.validatePassword
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    var name by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var codiceFiscale by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var cognomeError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var cfError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    RegisterEditProfileForm(
        title = "Registrazione",
        name = name,
        cognome = cognome,
        email = email,
        codiceFiscale = codiceFiscale,
        password = password,
        onNameChange = { name = it },
        onCognomeChange = { cognome = it },
        onEmailChange = { email = it },
        onCodiceFiscaleChange = { codiceFiscale = it },
        onPasswordChange = { password = it },
        actionButtonText = "Registrati",
        onActionClick = {
            nameError = checkFieldLength(name, 2, "Nome")
            cognomeError = checkFieldLength(cognome, 2, "Cognome")
            emailError = validateEmail(email)
            cfError = validateCodiceFiscale(codiceFiscale)
            passwordError = validatePassword(password)

            val hasErrors = listOf(nameError, cognomeError, emailError, cfError, passwordError).any { it != null }

            if (!hasErrors) {
                val user = User("", name, cognome, email, codiceFiscale, password)
                authViewModel.register(user)
            }
        },
        errorMessage = listOfNotNull(nameError, cognomeError, emailError, cfError, passwordError).firstOrNull()
            ?: authViewModel.authState.value?.takeIf { it.startsWith("FAILED") }
    )

    if (authViewModel.authState.value == "SUCCESS") {
        LaunchedEffect(Unit) {
            navController.navigate(LoginScreenRoute)
        }
    }
}
