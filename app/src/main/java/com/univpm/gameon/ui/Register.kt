package com.univpm.gameon.ui

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun RegisterFormContent(
    name: String,
    cognome: String,
    email: String,
    codiceFiscale: String,
    password: String,
    nameError: String?,
    cognomeError: String?,
    emailError: String?,
    cfError: String?,
    passwordError: String?,
    onNameChange: (String) -> Unit,
    onCognomeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCodiceFiscaleChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    errorMessage: String?
) {
    RegisterEditProfileForm(
        title = "Registrazione",
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
        actionButtonText = "Registrati",
        onActionClick = onRegisterClick,
        errorMessage = errorMessage
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterFormContentPreview() {
    RegisterFormContent(
        name = "Mario",
        cognome = "Rossi",
        email = "mario.rossi@email.com",
        codiceFiscale = "RSSMRA80A01H501U",
        password = "password123",
        nameError = null,
        cognomeError = null,
        emailError = null,
        cfError = "Codice fiscale non valido",
        passwordError = null,
        onNameChange = {},
        onCognomeChange = {},
        onEmailChange = {},
        onCodiceFiscaleChange = {},
        onPasswordChange = {},
        onRegisterClick = {},
        errorMessage = null
    )
}
