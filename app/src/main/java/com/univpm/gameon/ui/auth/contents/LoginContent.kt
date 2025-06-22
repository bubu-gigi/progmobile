package com.univpm.gameon.ui.auth.contents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.univpm.gameon.R
import com.univpm.gameon.ui.futuraBookFontFamily
import com.univpm.gameon.ui.lemonMilkFontFamily

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
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
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
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
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

