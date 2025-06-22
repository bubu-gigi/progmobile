package com.univpm.gameon.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.checkFieldLength
import com.univpm.gameon.core.validateCodiceFiscale
import com.univpm.gameon.core.validateEmail
import com.univpm.gameon.core.validatePassword
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.ui.components.RegisterEditProfileForm
import com.univpm.gameon.ui.futuraBookFontFamily
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

    RegisterFormContent(
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
        onRegisterClick = {
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
        errorMessage = listOfNotNull(
            nameError,
            cognomeError,
            emailError,
            cfError,
            passwordError
        ).firstOrNull() ?: authViewModel.authState.value?.takeIf { it.startsWith("FAILED") }
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
                onActionClick = onRegisterClick,
                errorMessage = errorMessage
            )
        }
    }
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
