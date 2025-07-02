package com.univpm.gameon.ui.auth

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.checkFieldLength
import com.univpm.gameon.core.validateCodiceFiscale
import com.univpm.gameon.core.validateEmail
import com.univpm.gameon.core.validatePassword
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.ui.components.UserForm
import com.univpm.gameon.viewmodels.AuthViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RegisterScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    var name by rememberSaveable { mutableStateOf("") }
    var cognome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var codiceFiscale by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var cognomeError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var cfError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val errorMessage = listOfNotNull(
        nameError,
        cognomeError,
        emailError,
        cfError,
        passwordError
    ).firstOrNull() ?: authViewModel.authState.value?.takeIf { it.startsWith("FAILED") }

    if (authViewModel.authState.value == "SUCCESS") {
        LaunchedEffect(Unit) {
            navController.navigate(LoginScreenRoute)
        }
    }

    UserForm(
        title = "Registrazione",
        name = name,
        cognome = cognome,
        email = email,
        codiceFiscale = codiceFiscale,
        password = password,
        nameError = nameError,
        cognomeError = cognomeError,
        emailError = emailError,
        cfError = cfError,
        passwordError = passwordError,
        onNameChange = { name = it },
        onCognomeChange = { cognome = it },
        onEmailChange = { email = it },
        onCodiceFiscaleChange = { codiceFiscale = it },
        onPasswordChange = { password = it },
        onSubmit = {
            nameError = checkFieldLength(name, 2, "Nome")
            cognomeError = checkFieldLength(cognome, 2, "Cognome")
            emailError = validateEmail(email)
            cfError = validateCodiceFiscale(codiceFiscale)
            passwordError = validatePassword(password)

            val hasErrors = listOf(nameError, cognomeError, emailError, cfError, passwordError).any { it != null }

            if (!hasErrors) {
                val user = User(
                    id = "",
                    name = name,
                    cognome = cognome,
                    email = email,
                    codiceFiscale = codiceFiscale,
                    password = password
                )
                authViewModel.register(user)
            }
        },
        submitButtonText = "Registrati",
        errorMessage = errorMessage
    )
}
