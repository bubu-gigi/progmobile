package com.univpm.gameon.ui.auth.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.univpm.gameon.R
import com.univpm.gameon.ui.components.RegisterEditProfileForm
import com.univpm.gameon.ui.futuraBookFontFamily


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
            displayLarge = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 57.sp,
                color = Color.White
            ),
            displayMedium = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 45.sp,
                color = Color.White
            ),
            displaySmall = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 36.sp,
                color = Color.White
            ),

            headlineLarge = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 32.sp,
                color = Color.White
            ),
            headlineMedium = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 28.sp,
                color = Color.White
            ),
            headlineSmall = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 24.sp,
                color = Color.White
            ),

            titleLarge = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 22.sp,
                color = Color.White
            ),
            titleMedium = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),
            titleSmall = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),

            bodyLarge = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 16.sp,
                color = Color.White
            ),
            bodyMedium = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 14.sp,
                color = Color.White
            ),
            bodySmall = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 12.sp,
                color = Color.White
            ),

            labelLarge = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),
            labelMedium = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),
            labelSmall = TextStyle(
                fontFamily = futuraBookFontFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),
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
                title = "Register",
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
                errorMessage = errorMessage,
            )
        }
    }
}