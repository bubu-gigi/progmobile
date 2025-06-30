package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.CarteListScreenRoute
import com.univpm.gameon.core.EditProfileScreenRoute
import com.univpm.gameon.viewmodels.AuthViewModel
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatListScreenRoute
import com.univpm.gameon.core.GiocatorePrenotazioniRoute
import androidx.compose.runtime.getValue
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.RoundedButtonComponent

@Composable
fun GiocatoreHomeScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    val destination by authViewModel.destination.collectAsState()

    LaunchedEffect(destination) {
        destination?.let {
            navController.navigate(it)
            authViewModel.clearDestination()
        }
    }

    val background = painterResource(id = R.drawable.sfondobase)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = background,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(465.dp))

            CustomText(
                text = "BENVENUTO!",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            RoundedButtonComponent(
                text = "Gestione Prenotazioni",
                onClick = { navController.navigate(GiocatorePrenotazioniRoute) },
                modifier = Modifier.padding(vertical = 6.dp)
            )
            RoundedButtonComponent(
                text = "Modifica Profilo",
                onClick = { navController.navigate(EditProfileScreenRoute) },
                modifier = Modifier.padding(vertical = 6.dp)
            )
            RoundedButtonComponent(
                text = "Gestione Carte",
                onClick = { navController.navigate(CarteListScreenRoute) },
                modifier = Modifier.padding(vertical = 6.dp)
            )
            RoundedButtonComponent(
                text = "Contatta Strutture",
                onClick = { navController.navigate(ChatListScreenRoute) },
                modifier = Modifier.padding(vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), RoundedCornerShape(12.dp))
                        .background(Color(0xFF6136FF), RoundedCornerShape(12.dp))
                        .clickable {
                            authViewModel.deleteAccount()
                        }
                        .padding(vertical = 15.dp)
                ) {
                    CustomText(
                        text = "Elimina Account",
                        fontSize = 18.sp,
                        color = Color(0xFFCFFF5E),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), RoundedCornerShape(12.dp))
                        .background(Color(0xFF6136FF), RoundedCornerShape(12.dp))
                        .clickable {
                            authViewModel.logout()
                        }
                        .padding(vertical = 15.dp)
                ) {
                    CustomText(
                        text = "Logout",
                        fontSize = 18.sp,
                        color = Color(0xFFCFFF5E),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
