package com.univpm.gameon.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.viewmodels.AuthViewModel

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.univpm.gameon.ui.components.RegisterEditProfileForm


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

    EditProfileFormContent(
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
        onSave = {

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
    MaterialTheme(
        typography = Typography(
            displayLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 57.sp, color = Color.White),
            displayMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 45.sp, color = Color.White),
            displaySmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 36.sp, color = Color.White),

            headlineLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 32.sp, color = Color.White),
            headlineMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 28.sp, color = Color.White),
            headlineSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 24.sp, color = Color.White),

            titleLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 22.sp, color = Color.White),
            titleMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White),
            titleSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White),

            bodyLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 16.sp, color = Color.White),
            bodyMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, color = Color.White),
            bodySmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 12.sp, color = Color.White),

            labelLarge = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White),
            labelMedium = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White),
            labelSmall = TextStyle(fontFamily = futuraBookFontFamily, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.White),
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.sfondocarta),
                contentDescription = "Sfondo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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
                onActionClick = onSave,
                errorMessage = errorMessage,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
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