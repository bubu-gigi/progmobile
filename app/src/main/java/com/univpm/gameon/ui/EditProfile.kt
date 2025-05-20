package com.univpm.gameon.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun EditProfileFormContent(
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
    onSave: () -> Unit,
    errorMessage: String?
) {
    RegisterEditProfileForm(
        title = "Modifica Profilo",
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
        actionButtonText = "Salva Modifiche",
        onActionClick = onSave,
        errorMessage = errorMessage
    )
}

@Composable
@Preview(showBackground = true)
fun EditProfileFormContentPreview() {
    EditProfileFormContent(
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
        onSave = {},
        errorMessage = null
    )
}
