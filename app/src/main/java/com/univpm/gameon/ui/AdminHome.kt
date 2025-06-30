package com.univpm.gameon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatListAdminScreenRoute
import com.univpm.gameon.core.EditProfileScreenRoute
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.StruttureListRoute
import com.univpm.gameon.ui.components.CustomText
import com.univpm.gameon.ui.components.RoundedButtonComponent
import com.univpm.gameon.viewmodels.AuthViewModel

@Composable
fun AdminHomeScreen(navController: NavController) {

    val authViewModel: AuthViewModel = hiltViewModel()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.sfondobase),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(470.dp))
            CustomText(text = "BENVENUTO!", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))
            RoundedButtonComponent(
                text = "Gestione Account",
                onClick = { navController.navigate(EditProfileScreenRoute) }
            )
            Spacer(modifier = Modifier.height(4.dp))
            RoundedButtonComponent(
                text = "Gestione Prenotazioni",
                onClick = { navController.navigate(ChatListAdminScreenRoute) }
            )
            Spacer(modifier = Modifier.height(4.dp))
            RoundedButtonComponent(
                text = "Gestione Strutture",
                onClick = { navController.navigate(StruttureListRoute) }
            )
            Spacer(modifier = Modifier.height(4.dp))
            RoundedButtonComponent(
                text = "Visualizza Messaggi",
                onClick = { navController.navigate(ChatListAdminScreenRoute) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(12.dp)) // Aggiunto il bordo con angoli arrotondati
                    .background(
                        color = Color(232323),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(onClick = {authViewModel.logout(); navController.navigate(
                        LoginScreenRoute)})
                    .padding(vertical = 15.dp, horizontal = 32.dp)
            ) {
                CustomText(text = "Logout", fontSize = 18.sp)
            }
        }
    }
}