package com.univpm.gameon.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.viewmodels.AuthViewModel

@Composable
fun EditProfileScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    val currentUser by authViewModel.currentUser

    var userId by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var cognome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var codiceFiscale by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) { authViewModel.loadCurrentUser() }

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

    RegisterEditProfileForm(
        title = "Modifica Profilo",
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
        actionButtonText = "Salva Modifiche",
        onActionClick = {
            val updatedUser = User(userId, name, cognome, email, codiceFiscale, password)
            Log.d("AuthViewModel", "Id ${updatedUser}")
            authViewModel.updateProfile(userId, updatedUser)
        },
        errorMessage = authViewModel.authState.value?.takeIf { it.startsWith("FAILED") }
    )

    if (authViewModel.authState.value == "UPDATED") {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }
}
