package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.RegisterScreenRoute
import com.univpm.gameon.core.validatePassword
import com.univpm.gameon.viewmodels.AuthViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Typography




@Composable
fun LoginScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authViewModel.destination.value) {
        authViewModel.destination.value?.let {
            navController.navigate(it)
            authViewModel.destination.value = null
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        emailError?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        passwordError?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val emailValidation = null
            val passwordValidation = validatePassword(password)

            emailError = emailValidation
            passwordError = passwordValidation

            if (emailValidation == null && passwordValidation == null) {
                authViewModel.login(email.trim().toLowerCase(), password.trim())
            }
        }) {
            Text("Login")
        }


        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate(RegisterScreenRoute)
        }) {
            Text("Non hai un account? Registrati")
        }
    }
}

@Composable
fun LoginContent(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
){
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


            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") }
                )
                emailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                passwordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .padding(18.dp)
                        .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp))
                        .background(
                            color = Color(0xFF6136FF),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = "Login",
                        color = Color(0xFFCFFF5E),
                        fontSize = 18.sp
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onRegisterClick) {
                    Text("Non hai un account? Registrati")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    LoginContent(
        email = "test@example.com",
        password = "password123",
        emailError = null,
        passwordError = "Password troppo corta",
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onRegisterClick = {}
    )
}
