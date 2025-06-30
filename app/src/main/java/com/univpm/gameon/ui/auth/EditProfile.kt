package com.univpm.gameon.ui.auth

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.viewmodels.AuthViewModel
import com.univpm.gameon.ui.components.UserForm
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun EditProfileScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUser by authViewModel.currentUser.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    var userId by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var cognome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var codiceFiscale by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.loadCurrentUser()
    }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            userId = it.id
            name = it.name
            cognome = it.cognome
            email = it.email
            codiceFiscale = it.codiceFiscale
            password = it.password
        }
    }

    UserForm(
        title = "Modifica Profilo",
        name = name,
        onNameChange = { name = it },
        nameError = null,
        cognome = cognome,
        onCognomeChange = { cognome = it },
        cognomeError = null,
        email = email,
        onEmailChange = { email = it },
        emailError = null,
        codiceFiscale = codiceFiscale,
        onCodiceFiscaleChange = { codiceFiscale = it },
        cfError = null,
        password = password,
        onPasswordChange = { password = it },
        passwordError = null,
        submitButtonText = "Salva modifiche",
        onSubmit = {
            val updatedUser = User(
                id = userId,
                name = name,
                cognome = cognome,
                email = email,
                codiceFiscale = codiceFiscale,
                password = password
            )
            Log.d("EditProfile", "Aggiornamento utente: $updatedUser")
            authViewModel.updateProfile(userId, updatedUser)
        },
        errorMessage = authState?.takeIf { it.startsWith("FAILED")
        }
    )

    if (authState == "UPDATED") {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }
}
