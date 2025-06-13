package com.univpm.gameon.ui.auth

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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.univpm.gameon.ui.futuraBookFontFamily
import com.univpm.gameon.ui.lemonMilkFontFamily

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

    LoginContent(
        email = email,
        password = password,
        emailError = emailError,
        passwordError = passwordError,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = {
            val emailValidation = null // Puoi aggiungere validateEmail() se vuoi
            val passwordValidation = validatePassword(password)

            emailError = emailValidation
            passwordError = passwordValidation

            if (emailValidation == null && passwordValidation == null) {
                authViewModel.login(email.trim().lowercase(), password.trim())
            }
        },
        onRegisterClick = {
            navController.navigate(RegisterScreenRoute)
        }
    )
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
                Text(text = "Login",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = lemonMilkFontFamily,
                    style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE36BE0),
                        unfocusedBorderColor = Color(0xFFE36BE0),
                        focusedLabelColor = Color(0xFFE36BE0),
                        unfocusedLabelColor = Color(0xFFE36BE0),
                        focusedTextColor = Color.White, // Colore del testo quando il campo è a fuoco
                        unfocusedTextColor = Color.White // Colore del testo quando il campo non è a fuoco
                    ),
                    label = {
                        Text(
                        text = "Email",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = futuraBookFontFamily
                        ) }
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE36BE0),
                        unfocusedBorderColor = Color(0xFFE36BE0),
                        focusedLabelColor = Color(0xFFE36BE0),
                        unfocusedLabelColor = Color(0xFFE36BE0),
                        focusedTextColor = Color.White, // Colore del testo quando il campo è a fuoco
                        unfocusedTextColor = Color.White // Colore del testo quando il campo non è a fuoco
                    ),
                    label = { Text(
                        text = "Password",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = futuraBookFontFamily
                    ) },
                    visualTransformation = PasswordVisualTransformation()
                )
                passwordError?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = futuraBookFontFamily,
                        color = Color(0xFFCFFF5E),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

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
