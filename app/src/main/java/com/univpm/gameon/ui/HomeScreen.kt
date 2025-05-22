package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.univpm.gameon.R

@Composable
fun HomeScreen(
    onAccediClick: () -> Unit,
    onRegistratiClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.sfondointro),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(470.dp))

            Button(
                onClick = onAccediClick,
                modifier = Modifier
                    .padding(20.dp)
                    .size(width = 190.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF232323),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Accedi",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = futuraBookFontFamily
                )
            }

            Button(
                onClick = onRegistratiClick,
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 190.dp, height = 50.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(120.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF232323),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Registrati",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = futuraBookFontFamily
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onAccediClick = {},
        onRegistratiClick = {}
    )
}

