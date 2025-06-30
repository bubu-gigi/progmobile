package com.univpm.gameon.ui.auth

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.RegisterScreenRoute
import com.univpm.gameon.core.validateEmail
import com.univpm.gameon.core.validatePassword
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.OutlinedInputField
import com.univpm.gameon.viewmodels.AuthViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.univpm.gameon.core.lemonMilkFontFamily

@Composable
fun LoginScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val destination by authViewModel.destination.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(destination) {
        destination?.let {
            navController.navigate(it)
            authViewModel.clearDestination()
        }
    }

    val errorMessage = listOfNotNull(emailError, passwordError)
        .firstOrNull() ?: authState?.takeIf { it.startsWith("FAILED") }

    BackgroundScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = lemonMilkFontFamily,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedInputField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                errorText = emailError
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedInputField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true,
                errorText = passwordError
            )

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            ButtonComponent(
                text = "Login",
                onClick = {
                    emailError = validateEmail(email)
                    passwordError = validatePassword(password)

                    if (emailError == null && passwordError == null) {
                        authViewModel.login(email.trim().lowercase(), password.trim())
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { navController.navigate(RegisterScreenRoute) }) {
                Text("Non hai un account? Registrati")
            }
        }
    }
}
